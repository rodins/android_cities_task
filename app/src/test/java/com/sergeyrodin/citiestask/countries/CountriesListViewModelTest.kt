package com.sergeyrodin.citiestask.countries

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.sergeyrodin.citiestask.data.source.FakeTestRepository
import com.sergeyrodin.citiestask.data.source.getOrAwaitValue
import com.sergeyrodin.citiestask.data.source.local.Country
import org.hamcrest.CoreMatchers.`is`
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class CountriesListViewModelTest{
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var repository: FakeTestRepository
    private lateinit var subject: CountriesListViewModel

    @Before
    fun initRepository() {
        repository = FakeTestRepository()
        subject = CountriesListViewModel(repository)
    }

    @Test
    fun inputCountry_nameEquals() {
        val country = Country(1, "Country")
        repository.addCountries(country)

        val loaded = subject.countries.getOrAwaitValue()
        assertThat(loaded[0].name, `is`(country.name))
    }

    @Test
    fun noCountries_sizeZero() {
        repository.addCountries()

        val loaded = subject.countries.getOrAwaitValue()
        assertThat(loaded.size, `is`(0))
    }

    @Test
    fun loadingMode_loadingIsTrue() {
        repository.loadingMode()

        val loaded = subject.loading.getOrAwaitValue()
        assertThat(loaded, `is`(true))
    }

    @Test
    fun errorMode_loadingFalse() {
        repository.errorMode()

        val loaded = subject.loading.getOrAwaitValue()
        assertThat(loaded, `is`(false))
    }

    @Test
    fun errorMode_errorEquals() {
        repository.errorMode()

        val loaded = subject.error.getOrAwaitValue()
        assertThat(loaded, `is`("Error"))
    }

    @Test
    fun countriesEmpty_loadFromNet_nameEquals() {
        val json = mapOf("Country" to listOf("City1", "City2", "City3"))
        repository.addJsonMap(json)

        subject.loadCountries()

        val loaded = subject.countries.getOrAwaitValue()
        assertThat(loaded[0].name, `is`("Country"))
    }

    @Test
    fun countriesNotEmpty_nameFromDbEquals() {
        val country = Country(1, "Country from db")
        repository.addCountries(country)
        val json = mapOf("Country from net" to listOf("City1", "City2", "City3"))
        repository.addJsonMap(json)

        val loaded = subject.countries.getOrAwaitValue()
        assertThat(loaded[0].name, `is`(country.name))
    }

    @Test
    fun refreshCountries_nameEquals() {
        val country = Country(1, "Country from db")
        repository.addCountries(country)
        val json = mapOf("Country from net" to listOf("City1", "City2", "City3"))
        repository.addJsonMap(json)

        subject.refresh()

        val loaded = subject.countries.getOrAwaitValue()
        assertThat(loaded[0].name, `is`("Country from net"))
    }
}