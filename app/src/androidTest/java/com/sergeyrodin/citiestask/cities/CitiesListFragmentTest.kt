package com.sergeyrodin.citiestask.cities

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.sergeyrodin.citiestask.FakeTestRepository
import com.sergeyrodin.citiestask.R
import com.sergeyrodin.citiestask.ServiceLocator
import com.sergeyrodin.citiestask.data.source.City
import com.sergeyrodin.citiestask.data.source.Country
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.Mockito.verify

@RunWith(AndroidJUnit4::class)
@MediumTest
class CitiesListFragmentTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

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
        val bundle = CitiesListFragmentArgs.Builder(country.id, country.name).build().toBundle()
        launchFragmentInContainer<CitiesListFragment>(bundle, R.style.AppTheme)

        onView(withText(city.name)).check(matches(isDisplayed()))
    }

    @Test
    fun cityClick_navigationCalled() {
        val country = Country(1, "Country")
        val city = City(1, "City", country.id)
        repository.addCountries(country)
        repository.addCities(city)
        val bundle = CitiesListFragmentArgs.Builder(country.id, country.name).build().toBundle()
        val scenario = launchFragmentInContainer<CitiesListFragment>(bundle, R.style.AppTheme)
        val navController = Mockito.mock(NavController::class.java)
        scenario.onFragment {
            Navigation.setViewNavController(it.view!!, navController)
        }

        onView(withText(city.name)).perform(click())

        verify(navController).navigate(
            CitiesListFragmentDirections.actionCitiesListFragmentToCityInfoFragment(city.name, country.name)
        )
    }

    @Test
    fun cityIconDisplayed() {
        val country = Country(1, "Country")
        val city = City(1, "City", country.id)
        repository.addCountries(country)
        repository.addCities(city)
        val bundle = CitiesListFragmentArgs.Builder(country.id, country.name).build().toBundle()
        launchFragmentInContainer<CitiesListFragment>(bundle, R.style.AppTheme)

        onView(withId(R.id.city_icon)).check(matches(isDisplayed()))
    }
}