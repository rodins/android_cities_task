package com.sergeyrodin.citiestask.data.source.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.sergeyrodin.citiestask.data.source.City
import com.sergeyrodin.citiestask.data.source.Country
import com.sergeyrodin.citiestask.util.getOrAwaitValue
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@SmallTest
class CitiesDatabaseDaoTest {
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var citiesDatabase: CitiesDatabase

    @Before
    fun initDb() {
        citiesDatabase = Room.inMemoryDatabaseBuilder(
            getApplicationContext(),
            CitiesDatabase::class.java)
            .allowMainThreadQueries()
            .build()
    }

    @After
    fun closeDb() = citiesDatabase.close()

    @Test
    fun insertAndGetCountry() {
        val country1 = Country(1, "Country1")
        val country2 = Country(2, "Country2")
        val countries = listOf(country1, country2)
        citiesDatabase.citiesDatabaseDao.insertCountries(countries)

        val list = citiesDatabase.citiesDatabaseDao.getCountries().getOrAwaitValue()
        assertThat(list[0].name, `is`(country1.name))
    }

    @Test
    fun insertAndGetCountryFromList() {
        val country1 = Country(1, "Country1")
        val country2 = Country(2, "Country2")
        val countries = listOf(country1, country2)
        citiesDatabase.citiesDatabaseDao.insertCountries(countries)

        val list = citiesDatabase.citiesDatabaseDao.getCountriesList()
        assertThat(list[0].name, `is`(country1.name))
    }

    @Test
    fun insertAndGetCity() {
        val country1 = Country(1, "Country1")
        val country2 = Country(2, "Country2")
        val countries = listOf(country1, country2)
        val city1 = City(1, "City1", country1.id)
        val city2 = City(2, "City2", country1.id)
        val city3 = City(3, "City3", country2.id)
        val city4 = City(4, "City4", country2.id)
        val cities = listOf(city1, city2, city3, city4)
        citiesDatabase.citiesDatabaseDao.insertCountries(countries)
        citiesDatabase.citiesDatabaseDao.insertCity(cities)

        val loaded = citiesDatabase.citiesDatabaseDao.getCitiesByCountryId(country1.id)
        assertThat(loaded[0].name, `is`(cities[0].name))
    }

    @Test
    fun deleteCountries_sizeZero() {
        val country1 = Country(1, "Country1")
        val country2 = Country(2, "Country2")
        val countries = listOf(country1, country2)
        citiesDatabase.citiesDatabaseDao.insertCountries(countries)
        citiesDatabase.citiesDatabaseDao.deleteAllCountries()

        val loaded = citiesDatabase.citiesDatabaseDao.getCountries().getOrAwaitValue()
        assertThat(loaded.size, `is`(0))
    }
}