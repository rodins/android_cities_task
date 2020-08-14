package com.sergeyrodin.citiestask.data.source.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
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
        val country =
            Country(1, "Country")

        citiesDatabase.citiesDatabaseDao.insertCountry(country)

        val list = citiesDatabase.citiesDatabaseDao.getCountries().getOrAwaitValue()
        assertThat(list[0].name, `is`(country.name))
    }

    @Test
    fun insertAndGetCity() {
        val country =
            Country(1, "Country")
        val city = City(
            1,
            "City",
            country.id
        )
        citiesDatabase.citiesDatabaseDao.insertCountry(country)
        citiesDatabase.citiesDatabaseDao.insertCity(city)

        val cities = citiesDatabase.citiesDatabaseDao.getCitiesByCountryId(country.id)
        assertThat(cities[0].name, `is`(city.name))
    }

    @Test
    fun deleteCountries_sizeZero() {
        val country = Country(1, "Country")
        citiesDatabase.citiesDatabaseDao.insertCountry(country)
        citiesDatabase.citiesDatabaseDao.deleteAllCountries()

        val countries = citiesDatabase.citiesDatabaseDao.getCountries().getOrAwaitValue()
        assertThat(countries.size, `is`(0))
    }
}