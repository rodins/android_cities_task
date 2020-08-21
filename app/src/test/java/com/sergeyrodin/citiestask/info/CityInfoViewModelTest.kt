package com.sergeyrodin.citiestask.info

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.sergeyrodin.citiestask.CityInfoFakePresenter
import com.sergeyrodin.citiestask.data.source.getOrAwaitValue
import com.sergeyrodin.citiestask.info.view.CityInfoViewModel
import org.hamcrest.CoreMatchers.`is`
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class CityInfoViewModelTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var presenter: CityInfoFakePresenter
    private lateinit var subject: CityInfoViewModel

    @Before
    fun initDataSource() {
        presenter = CityInfoFakePresenter()
        subject = CityInfoViewModel(presenter)
    }

    @Test
    fun cityInput_titleEquals() {
        val country = "Country"
        val city = "City"
        presenter.dataMode()
        subject.start(country, city)

        val cityInfo = subject.cityInfo.getOrAwaitValue()
        assertThat(cityInfo.title, `is`(city))
        assertThat(cityInfo.summary, `is`(country))
    }

    @Test
    fun loadingMode_loadingTrue() {
        val country = "Country"
        val city = "City"
        presenter.loadingMode()
        subject.start(country, city)

        val loading = subject.loading.getOrAwaitValue()
        assertThat(loading, `is`(true))
    }

    @Test
    fun errorMode_textEquals() {
        val country = "Country"
        val city = "City"
        presenter.errorMode()
        subject.start(country, city)

        val error = subject.error.getOrAwaitValue()
        assertThat(error, `is`("Error"))
    }

    @Test
    fun cityInput_loadingFalse() {
        val country = "Country"
        val city = "City"
        presenter.dataMode()
        subject.start(country, city)

        val loading = subject.loading.getOrAwaitValue()
        assertThat(loading, `is`(false))
    }

    @Test
    fun cityInput_errorEmpty() {
        val country = "Country"
        val city = "City"
        presenter.dataMode()
        subject.start(country, city)

        val error = subject.error.getOrAwaitValue()
        assertThat(error, `is`(""))
    }

    @Test
    fun cityInput_dataVisible() {
        val country = "Country"
        val city = "City"
        presenter.dataMode()
        subject.start(country, city)

        val visible = subject.dataVisible.getOrAwaitValue()
        assertThat(visible, `is`(true))
    }

    @Test
    fun loadingMode_dataNotVisible() {
        val country = "Country"
        val city = "City"
        presenter.loadingMode()
        subject.start(country, city)

        val visible = subject.dataVisible.getOrAwaitValue()
        assertThat(visible, `is`(false))
    }

    @Test
    fun errorMode_dataNotVisible() {
        val country = "Country"
        val city = "City"
        presenter.errorMode()
        subject.start(country, city)

        val visible = subject.dataVisible.getOrAwaitValue()
        assertThat(visible, `is`(false))
    }

}