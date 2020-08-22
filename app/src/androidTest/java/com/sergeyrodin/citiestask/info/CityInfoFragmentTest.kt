package com.sergeyrodin.citiestask.info

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.sergeyrodin.citiestask.R
import com.sergeyrodin.citiestask.ServiceLocator
import com.sergeyrodin.citiestask.info.view.CityInfoFragment
import com.sergeyrodin.citiestask.info.view.CityInfoFragmentArgs
import org.hamcrest.CoreMatchers.not
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CityInfoFragmentTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var presenter: CityInfoFakePresenter

    @Before
    fun initDataSource() {
        presenter = CityInfoFakePresenter()
        ServiceLocator.cityInfoPresenter = presenter
    }

    @After
    fun resetDataSource() {
        ServiceLocator.resetCityInfoPresenter()
    }

    @Test
    fun cityNameDisplayed() {
        val country = "Country"
        val city = "City"
        presenter.dataMode()
        val bundle = CityInfoFragmentArgs.Builder(city, country).build().toBundle()
        launchFragmentInContainer<CityInfoFragment>(bundle, R.style.AppTheme)

        onView(withText(city)).check(matches(isDisplayed()))
    }

    @Test
    fun countryNameDisplayedAsSummary() {
        val country = "Country"
        val city = "City"
        presenter.dataMode()
        val bundle = CityInfoFragmentArgs.Builder(city, country).build().toBundle()
        launchFragmentInContainer<CityInfoFragment>(bundle, R.style.AppTheme)

        onView(withText(country)).check(matches(isDisplayed()))
    }

    @Test
    fun latitudeLongitudeEquals() {
        val country = "Country"
        val city = "City"
        presenter.dataMode()
        val bundle = CityInfoFragmentArgs.Builder(city, country).build().toBundle()
        launchFragmentInContainer<CityInfoFragment>(bundle, R.style.AppTheme)

        onView(withText("1234")).check(matches(isDisplayed())) // latitude
        onView(withText("5678")).check(matches(isDisplayed())) // longitude
    }

    @Test
    fun loadingMode_loadingIndicatorVisible() {
        val country = "Country"
        val city = "City"
        presenter.loadingMode()
        val bundle = CityInfoFragmentArgs.Builder(city, country).build().toBundle()
        launchFragmentInContainer<CityInfoFragment>(bundle, R.style.AppTheme)

        onView(withId(R.id.info_loading_indicator)).check(matches(isDisplayed()))
    }

    @Test
    fun loadingMode_titleNotDisplayed() {
        val country = "Country"
        val city = "City"
        presenter.loadingMode()
        val bundle = CityInfoFragmentArgs.Builder(city, country).build().toBundle()
        launchFragmentInContainer<CityInfoFragment>(bundle, R.style.AppTheme)

        onView(withText(city)).check(matches(not(isDisplayed())))
    }

    @Test
    fun dataMode_loadingNotDisplayed() {
        val country = "Country"
        val city = "City"
        presenter.dataMode()
        val bundle = CityInfoFragmentArgs.Builder(city, country).build().toBundle()
        launchFragmentInContainer<CityInfoFragment>(bundle, R.style.AppTheme)

        onView(withId(R.id.info_loading_indicator)).check(matches(not(isDisplayed())))
    }

    @Test
    fun errorMode_titleNotDisplayed() {
        val country = "Country"
        val city = "City"
        presenter.errorMode()
        val bundle = CityInfoFragmentArgs.Builder(city, country).build().toBundle()
        launchFragmentInContainer<CityInfoFragment>(bundle, R.style.AppTheme)

        onView(withText(city)).check(matches(not(isDisplayed())))
    }

    @Test
    fun cityInfoEmpty_textDisplayed() {
        val country = "Country"
        val city = "City"
        presenter.emptyMode()
        val bundle = CityInfoFragmentArgs.Builder(city, country).build().toBundle()
        launchFragmentInContainer<CityInfoFragment>(bundle, R.style.AppTheme)

        onView(withText(R.string.no_info_text)).check(matches(isDisplayed()))
    }
}