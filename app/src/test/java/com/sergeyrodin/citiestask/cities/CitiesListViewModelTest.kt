package com.sergeyrodin.citiestask.cities

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.sergeyrodin.citiestask.data.source.FakeTestRepository
import com.sergeyrodin.citiestask.data.source.getOrAwaitValue
import com.sergeyrodin.citiestask.data.source.City
import com.sergeyrodin.citiestask.data.source.Country
import org.hamcrest.CoreMatchers.`is`
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class CitiesListViewModelTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var repository: FakeTestRepository
    private lateinit var subject: CitiesListViewModel

    @Before
    fun initRepository() {
        repository = FakeTestRepository()
        subject = CitiesListViewModel(repository)
    }

    @Test
    fun cityInput_nameEquals() {
        val country = Country(1, "Country")
        val city = City(1, "City", country.id)
        repository.addCountries(country)
        repository.addCities(city)
        subject.start(country.id)

        val loaded = subject.cities.getOrAwaitValue()
        assertThat(loaded[0].name, `is`(city.name))
    }
}