package com.sergeyrodin.citiestask.countries

import android.content.Context
import androidx.appcompat.view.menu.ActionMenuItem
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.sergeyrodin.citiestask.FakeTestRepository
import com.sergeyrodin.citiestask.R
import com.sergeyrodin.citiestask.ServiceLocator
import com.sergeyrodin.citiestask.data.source.local.Country
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.Mockito.verify

@RunWith(AndroidJUnit4::class)
@MediumTest
class CountriesListFragmentTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var repository: FakeTestRepository

    @Before
    fun initRepository() {
        repository = FakeTestRepository()
        ServiceLocator.citiesRepository = repository
    }

    @After
    fun clearRepository() {
        ServiceLocator.resetRepository()
    }

    @Test
    fun countryInput_countryDisplayed() {
        val country = Country(1, "Country")
        repository.addCountries(country)
        launchFragmentInContainer<CountriesListFragment>(null, R.style.AppTheme)

        onView(withText(country.name)).check(matches(isDisplayed()))
    }

    @Test
    fun countryClick_navigationCalled() {
        val country = Country(1, "Country")
        repository.addCountries(country)
        val scenario = launchFragmentInContainer<CountriesListFragment>(null, R.style.AppTheme)
        val navController = Mockito.mock(NavController::class.java)
        scenario.onFragment{
            Navigation.setViewNavController(it.view!!, navController)
        }

        onView(withText(country.name)).perform(click())

        verify(navController).navigate(
             CountriesListFragmentDirections.actionCountriesLIstFragmentToCitiesListFragment(country.id)
        )
    }

    @Test
    fun loadingMode_loadingDisplayed() {
        repository.loadingMode()

        launchFragmentInContainer<CountriesListFragment>(null, R.style.AppTheme)

        onView(withId(R.id.loading_indicator)).check(matches(isDisplayed()))
    }

    @Test
    fun errorMode_errorDisplayed() {
        repository.errorMode()

        launchFragmentInContainer<CountriesListFragment>(null, R.style.AppTheme)

        onView(withText("Error")).check(matches(isDisplayed()))
    }

    @Test
    fun countriesEmpty_loadFromNet_nameDisplayed() {
        repository.addCountries()
        val json = mapOf("Country" to listOf("City1", "City2", "City3"))
        repository.addJsonMap(json)
        launchFragmentInContainer<CountriesListFragment>(null, R.style.AppTheme)

        onView(withText("Country")).check(matches(isDisplayed()))
    }

    @Test
    fun refreshCountries_nameDisplayed() {
        val country = Country(1, "Country from db")
        repository.addCountries()
        val json = mapOf("Country from net" to listOf("City1", "City2", "City3"))
        repository.addJsonMap(json)
        val scenario = launchFragmentInContainer<CountriesListFragment>(null, R.style.AppTheme)

        clickRefreshAction(scenario)

        onView(withText("Country from net")).check(matches(isDisplayed()))
    }

    private fun clickRefreshAction(scenario: FragmentScenario<CountriesListFragment>) {
        // Create dummy menu item with the desired item id
        val context = ApplicationProvider.getApplicationContext<Context>()
        val editMenuItem = ActionMenuItem(context, 0, R.id.action_refresh, 0, 0, null)
        scenario.onFragment{
            it.onOptionsItemSelected(editMenuItem)
        }
    }
}