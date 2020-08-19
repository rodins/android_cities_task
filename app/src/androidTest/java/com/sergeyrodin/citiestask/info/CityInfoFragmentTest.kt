package com.sergeyrodin.citiestask.info

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.sergeyrodin.citiestask.R
import com.sergeyrodin.citiestask.ServiceLocator
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CityInfoFragmentTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var dataSource: CityInfoFakeDataSource

    @Before
    fun initDataSource() {
        dataSource = CityInfoFakeDataSource()
        ServiceLocator.cityInfoDataSource = dataSource
    }

    @After
    fun resetDataSource() {
        ServiceLocator.resetCityInfoDataSource()
    }

    @Test
    fun cityNameDisplayed() {
        val country = "Country"
        val city = "City"

        val bundle = CityInfoFragmentArgs.Builder(city, country).build().toBundle()
        launchFragmentInContainer<CityInfoFragment>(bundle, R.style.AppTheme)

        onView(withText(city)).check(matches(isDisplayed()))
    }

    @Test
    fun countryNameDisplayedAsSummary() {
        val country = "Country"
        val city = "City"

        val bundle = CityInfoFragmentArgs.Builder(city, country).build().toBundle()
        launchFragmentInContainer<CityInfoFragment>(bundle, R.style.AppTheme)

        onView(withText(country)).check(matches(isDisplayed()))
    }

    @Test
    fun latitudeLongitudeEquals() {
        val country = "Country"
        val city = "City"

        val bundle = CityInfoFragmentArgs.Builder(city, country).build().toBundle()
        launchFragmentInContainer<CityInfoFragment>(bundle, R.style.AppTheme)

        onView(withText("1234")).check(matches(isDisplayed())) // latitude
        onView(withText("5678")).check(matches(isDisplayed())) // longitude
    }
}