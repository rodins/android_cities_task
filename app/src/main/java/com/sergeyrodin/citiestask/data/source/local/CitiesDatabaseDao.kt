package com.sergeyrodin.citiestask.data.source.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface CitiesDatabaseDao {
    @Insert
    fun insertCountry(country: Country): Long

    @Query("SELECT * FROM countries")
    fun getCountries(): List<Country>

    @Insert
    fun insertCity(city: City)

    @Query("SELECT * FROM cities WHERE country_id = :countryId")
    fun getCitiesByCountryId(countryId: Long): List<City>
}