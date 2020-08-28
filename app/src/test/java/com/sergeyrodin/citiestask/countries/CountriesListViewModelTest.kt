package com.sergeyrodin.citiestask.countries

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.sergeyrodin.citiestask.data.source.FakeCitiesRepository
import com.sergeyrodin.citiestask.data.source.getOrAwaitValue
import com.sergeyrodin.citiestask.data.source.Country
import com.sergeyrodin.citiestask.data.source.FakeCountriesRepository
import org.hamcrest.CoreMatchers.`is`
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class CountriesListViewModelTest{
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var repository: FakeCountriesRepository
    private lateinit var subject: CountriesListViewModel

    @Before
    fun initRepository() {
        repository = FakeCountriesRepository()
        subject = CountriesListViewModel(repository)
    }

    @Test
    fun inputCountry_nameEquals() {
        val country = Country(1, "Country")
        repository.addCountries(country)

        subject.start()

        val loaded = subject.countries.getOrAwaitValue()
        assertThat(loaded[0].name, `is`(country.name))
    }

    @Test
    fun noCountries_sizeZero() {
        repository.addCountries()

        subject.start()

        val loaded = subject.countries.getOrAwaitValue()
        assertThat(loaded.size, `is`(0))
    }

    @Test
    fun errorMode_errorEquals() {
        repository.errorMode()

        subject.start()

        val loaded = subject.error.getOrAwaitValue()
        assertThat(loaded, `is`("Error"))
    }

    @Test
    fun countriesEmpty_loadFromNet_nameEquals() {
        val json = mapOf("Country" to listOf("City1", "City2", "City3"))
        repository.addJsonMap(json)

        subject.start()

        val loaded = subject.countries.getOrAwaitValue()
        assertThat(loaded[0].name, `is`("Country"))
    }

    @Test
    fun countriesNotEmpty_nameFromDbEquals() {
        val country = Country(1, "Country from db")
        repository.addCountries(country)
        val json = mapOf("Country from net" to listOf("City1", "City2", "City3"))
        repository.addJsonMap(json)

        subject.start()

        val loaded = subject.countries.getOrAwaitValue()
        assertThat(loaded[0].name, `is`(country.name))
    }

    @Test
    fun refreshCountries_nameEquals() {
        val country = Country(1, "Country from db")
        repository.addCountries(country)
        val json = mapOf("Country from net" to listOf("City1", "City2", "City3"))
        repository.addJsonMap(json)

        subject.start()

        subject.refresh()

        val loaded = subject.countries.getOrAwaitValue()
        assertThat(loaded[0].name, `is`("Country from net"))
    }
}