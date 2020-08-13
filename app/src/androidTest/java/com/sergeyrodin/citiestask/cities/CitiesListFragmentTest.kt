package com.sergeyrodin.citiestask.cities

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.sergeyrodin.citiestask.FakeTestRepository
import com.sergeyrodin.citiestask.R
import com.sergeyrodin.citiestask.ServiceLocator
import com.sergeyrodin.citiestask.data.source.local.City
import com.sergeyrodin.citiestask.data.source.local.Country
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@MediumTest
class CitiesListFragmentTest {
    private lateinit var repository: FakeTestRepository

    @Before
    fun initRepository() {
        repository = FakeTestRepository()
        ServiceLocator.citiesRepository = repository
    }

    @After
    fun resetRepository() {
        ServiceLocator.resetRepository()
    }

    @Test
    fun inputCity_nameDisplayed() {
        val country = Country(1, "Country")
        val city = City(1, "City", country.id)
        repository.addCountries(country)
        repository.addCities(city)
        val bundle = CitiesListFragmentArgs.Builder(country.id).build().toBundle()
        launchFragmentInContainer<CitiesListFragment>(bundle, R.style.AppTheme)

        onView(withText(city.name)).check(matches(isDisplayed()))
    }
}