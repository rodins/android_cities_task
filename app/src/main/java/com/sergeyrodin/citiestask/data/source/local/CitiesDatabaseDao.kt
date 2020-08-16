package com.sergeyrodin.citiestask.data.source.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface CitiesDatabaseDao {
    @Insert
    fun insertCountries(countries: List<Country>)

    @Query("SELECT * FROM countries")
    fun getCountries(): LiveData<List<Country>>

    @Insert
    fun insertCity(cities: List<City>)

    @Query("SELECT * FROM cities WHERE country_id = :countryId")
    fun getCitiesByCountryId(countryId: Long): List<City>

    @Query("DELETE FROM countries")
    fun deleteAllCountries()

    @Query("DELETE FROM cities")
    fun deleteAllCities()
}