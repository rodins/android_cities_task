package com.sergeyrodin.citiestask.data.source

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
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

    private lateinit var localDataSource: FakeLocalDataSource
    private lateinit var subject: CitiesDefaultRepository

    @Before
    fun initDataSource() {
        localDataSource = FakeLocalDataSource()
        subject = CitiesDefaultRepository(
            localDataSource
        )
    }

    @Test
    fun getCities_sizeEquals() = runBlockingTest{
        val countryId = 1L
        val city1 = City(1, "City1", countryId)
        val city2 = City(2, "City2", countryId)
        val city3 = City(3, "City3", countryId)
        localDataSource.addCities(city1, city2, city3)

        val citiesLoaded = subject.fetchCitiesByCountryId(countryId)
        assertThat(citiesLoaded.size, `is`(3))
    }

}