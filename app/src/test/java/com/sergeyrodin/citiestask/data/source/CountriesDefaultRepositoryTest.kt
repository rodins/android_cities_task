package com.sergeyrodin.citiestask.data.source

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.sergeyrodin.citiestask.data.source.local.FakeLocalDataSource
import com.sergeyrodin.citiestask.data.source.remote.FakeRemoteDataSource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class CountriesDefaultRepositoryTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val remoteCountriesMap = mapOf(
        "Country4" to listOf("City10", "City11", "City12"),
        "Country5" to listOf("City13", "City14", "City15", "City19"),
        "Country6" to listOf("City16", "City17", "City18"),
        "" to listOf()
    )

    private lateinit var localDataSource: FakeLocalDataSource
    private lateinit var remoteDataSource: FakeRemoteDataSource
    private lateinit var subject: CountriesDefaultRepository

    @Before
    fun initDataSource() {
        remoteDataSource = FakeRemoteDataSource(remoteCountriesMap)
        localDataSource = FakeLocalDataSource()
        subject = CountriesDefaultRepository(
            remoteDataSource,
            localDataSource
        )
    }

    @Test
    fun getCountries_sizeEquals() {
        val country1 = Country(1, "Country1")
        val country2 = Country(2, "Country2")
        val country3 = Country(3, "Country3")
        localDataSource.addCountries(country1, country2, country3)

        val countriesLoaded = subject.getCountries().getOrAwaitValue()
        MatcherAssert.assertThat(countriesLoaded.size, `is`(3))
    }

    @Test
    fun getRemoteCountries_countriesSizeEquals() = runBlockingTest{
        subject.loadCountriesAndCitiesToDb()

        val countriesLoaded = subject.getCountries().getOrAwaitValue()
        MatcherAssert.assertThat(countriesLoaded.size, `is`(3))
    }

    @Test
   fun getRemoteCountries_citiesSizeEquals() = runBlockingTest {
       subject.loadCountriesAndCitiesToDb()

       val countriesLoaded = subject.getCountries().getOrAwaitValue()
       val country5 = countriesLoaded.find {
           it.name == "Country5"
       }
       val citiesLoaded = localDataSource.getCitiesByCountryId(country5?.id!!)
       assertThat(citiesLoaded.size, `is`(4))
   }

    @Test
    fun errorMode_errorEquals() {
        remoteDataSource.setError()

        val error = subject.error.getOrAwaitValue()
        MatcherAssert.assertThat(error, `is`("Error"))
    }
}