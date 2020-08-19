package com.sergeyrodin.citiestask.info

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.sergeyrodin.citiestask.data.source.getOrAwaitValue
import org.hamcrest.CoreMatchers.`is`
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class CityInfoViewModelTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var dataSource: CityInfoFakeDataSource
    private lateinit var subject: CityInfoViewModel

    @Before
    fun initDataSource() {
        dataSource = CityInfoFakeDataSource()
        subject = CityInfoViewModel(dataSource)
    }

    @Test
    fun cityInput_titleEquals() {
        val country = "Country"
        val city = "City"
        dataSource.dataMode()
        subject.start(country, city)

        val cityInfo = subject.cityInfo.getOrAwaitValue()
        assertThat(cityInfo.title, `is`(city))
        assertThat(cityInfo.summary, `is`(country))
    }

    @Test
    fun loadingMode_loadingTrue() {
        val country = "Country"
        val city = "City"
        dataSource.loadingMode()
        subject.start(country, city)

        val loading = subject.loading.getOrAwaitValue()
        assertThat(loading, `is`(true))
    }

    @Test
    fun errorMode_textEquals() {
        val country = "Country"
        val city = "City"
        dataSource.errorMode()
        subject.start(country, city)

        val error = subject.error.getOrAwaitValue()
        assertThat(error, `is`("Error"))
    }

}