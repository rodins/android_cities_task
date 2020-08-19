package com.sergeyrodin.citiestask

import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.sergeyrodin.citiestask.data.source.CitiesRepository
import com.sergeyrodin.citiestask.data.source.local.City
import com.sergeyrodin.citiestask.data.source.local.Country
import com.sergeyrodin.citiestask.util.DataBindingIdlingResource
import com.sergeyrodin.citiestask.util.EspressoIdlingResource
import com.sergeyrodin.citiestask.util.monitorActivity
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.not
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
@LargeTest
class MainActivityTest {
    private lateinit var repository: CitiesRepository
    private val dataBindingIdlingResource = DataBindingIdlingResource()

    @Before
    fun registerIdlingResource() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.countingIdlingResource)
        IdlingRegistry.getInstance().register(dataBindingIdlingResource)
    }

    @After
    fun unregisterIdlingResource() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.countingIdlingResource)
        IdlingRegistry.getInstance().unregister(dataBindingIdlingResource)
    }

    @Before
    fun init() {
        repository = ServiceLocator.provideCitiesRepository(getApplicationContext())
        runBlocking {
            repository.deleteAllCountries()
        }
    }

    @After
    fun reset() {
        ServiceLocator.resetRepository()
    }

    @Test
    fun countryInput_countryClick_cityNameDisplayed() = runBlocking {
        val country1 = Country(1, "Country1")
        val country2 = Country(2, "Country2")
        val countries = listOf(country1, country2)
        val city1 = City(1, "City1", country1.id)
        val city2 = City(2, "City2", country1.id)
        val cities = listOf(city1, city2)
        repository.insertCountries(countries)
        repository.insertCities(cities)
        val activityScenario = ActivityScenario.launch(MainActivity::class.java)
        dataBindingIdlingResource.monitorActivity(activityScenario)

        onView(withText(country1.name)).perform(click())

        onView(withText(cities[0].name)).check(matches(isDisplayed()))

        activityScenario.close()
    }

    @Test
    fun countryClicked_titleEquals() = runBlocking {
        val country1 = Country(1, "Country1")
        val country2 = Country(2, "Country2")
        val countries = listOf(country1, country2)
        val city1 = City(1, "City1", country1.id)
        val city2 = City(2, "City2", country1.id)
        val cities = listOf(city1, city2)
        repository.insertCountries(countries)
        repository.insertCities(cities)
        val activityScenario = ActivityScenario.launch(MainActivity::class.java)
        dataBindingIdlingResource.monitorActivity(activityScenario)

        onView(withText(country1.name)).perform(click())

        onView(withText(cities[0].name)).check(matches(isDisplayed()))
        onView(withText(country1.name)).check(matches(isDisplayed()))

        activityScenario.close()
    }

    @Test
    fun cityClicked_titleDisplayed() = runBlocking {
        val country1 = Country(1, "Country1")
        val country2 = Country(2, "Country2")
        val countries = listOf(country1, country2)
        val city1 = City(1, "City1", country1.id)
        val city2 = City(2, "City2", country1.id)
        val cities = listOf(city1, city2)
        repository.insertCountries(countries)
        repository.insertCities(cities)
        val activityScenario = ActivityScenario.launch(MainActivity::class.java)
        dataBindingIdlingResource.monitorActivity(activityScenario)

        onView(withText(country1.name)).perform(click())
        onView(withText(city1.name)).perform(click())

        onView(withId(R.id.info_loading_indicator)).check(matches(isDisplayed()))
        onView(withText(city1.name)).check(matches(isDisplayed()))

        activityScenario.close()
    }

}