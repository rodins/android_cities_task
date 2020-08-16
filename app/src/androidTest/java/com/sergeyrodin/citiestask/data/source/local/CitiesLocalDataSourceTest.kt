package com.sergeyrodin.citiestask.data.source.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.sergeyrodin.citiestask.util.getOrAwaitValue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.CoreMatchers.`is`
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class CitiesLocalDataSourceTest {
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var citiesDatabase: CitiesDatabase
    private lateinit var subject: CitiesLocalDataSource

    @Before
    fun init() {
        citiesDatabase = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            CitiesDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        subject =
            CitiesLocalDataSource(
                citiesDatabase.citiesDatabaseDao,
                Dispatchers.Unconfined
            )
    }

    @After
    fun closeDb() = citiesDatabase.close()

    @Test
    fun insertAndGetCountry() = runBlockingTest{
        val country =
            Country(1, "Country")
        subject.insertCountry(country)

        val countries = subject.getCountries().getOrAwaitValue()
        assertThat(countries[0].name, `is`(country.name))
    }

    @Test
    fun insertAndGetCity() = runBlockingTest{
        val country =
            Country(1, "Country")
        val city1 = City(
            1,
            "City1",
            country.id
        )
        val city2 = City(2, "City2", country.id)
        val cities = listOf(city1, city2)
        subject.insertCountry(country)
        subject.insertCities(cities)

        val loaded = subject.getCitiesByCountryId(country.id)
        assertThat(loaded[0].name, `is`(cities[0].name))
    }
}