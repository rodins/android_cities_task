package com.sergeyrodin.citiestask.data.source

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.sergeyrodin.citiestask.data.source.CitiesDefaultRepository
import com.sergeyrodin.citiestask.data.source.local.City
import com.sergeyrodin.citiestask.data.source.local.Country
import com.sergeyrodin.citiestask.data.source.local.FakeLocalDataSource
import com.sergeyrodin.citiestask.data.source.remote.FakeRemoteDataSource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class CitiesDefaultRepositoryTest{
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val remoteCountriesMap = mapOf(
        "Country4" to listOf("City10", "City11", "City12"),
        "Country5" to listOf("City13", "City14", "City15", "City19"),
        "Country6" to listOf("City16", "City17", "City18")
    )

    private val country1 = Country(1, "Country1")
    private val country2 = Country(2, "Country2")
    private val country3 = Country(3, "Country3")
    private val city1 = City(1, "City1", country1.id)
    private val city2 = City(2, "City2", country1.id)
    private val city3 = City(3, "City3", country1.id)
    private val city4 = City(4, "City4", country2.id)
    private val city5 = City(5, "City5", country2.id)
    private val city6 = City(6, "City6", country2.id)
    private val city7 = City(7, "City7", country3.id)
    private val city8 = City(8, "City8", country3.id)
    private val city9 = City(9, "City9", country3.id)
    private val countries = mutableListOf(country1, country2, country3)
    private val cities = mutableListOf(
        city1, city2, city3, city4, city5, city6, city7, city8, city9)

    private lateinit var localDataSource: FakeLocalDataSource
    private lateinit var remoteDataSource: FakeRemoteDataSource
    private lateinit var subject: CitiesDefaultRepository

    @Before
    fun initDataSource() {
        remoteDataSource = FakeRemoteDataSource(remoteCountriesMap)
        localDataSource = FakeLocalDataSource(countries, cities)
        subject = CitiesDefaultRepository(
            remoteDataSource,
            localDataSource
        )
    }

    @Test
    fun getCountries_sizeEquals() = runBlockingTest{
        val countriesLoaded = subject.getCountries()
        assertThat(countriesLoaded.size, `is`(countries.size))
    }

    @Test
    fun getCities_sizeEquals() = runBlockingTest{
        val citiesLoaded = subject.getCitiesByCountryId(country1.id)
        assertThat(citiesLoaded.size, `is`(3))
    }

    @Test
    fun getRemoteCountries_countriesSizeEquals() = runBlockingTest{
        subject.loadCountriesAndCitiesToDb()

        val countriesLoaded = subject.getCountries()
        assertThat(countriesLoaded.size, `is`(6))
    }

    @Test
    fun getRemoteCountries_citiesSizeEquals() = runBlockingTest {
        subject.loadCountriesAndCitiesToDb()

        val countriesLoaded = subject.getCountries()
        val country5 = countriesLoaded.find {
            it.name == "Country5"
        }
        val citiesLoaded = subject.getCitiesByCountryId(country5?.id?:-1L)
        assertThat(citiesLoaded.size, `is`(4))
    }
}