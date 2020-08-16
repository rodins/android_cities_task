package com.sergeyrodin.citiestask.data.source.local

import androidx.lifecycle.LiveData
import com.sergeyrodin.citiestask.data.source.local.City
import com.sergeyrodin.citiestask.data.source.local.Country

interface ICitiesLocalDataSource {
    suspend fun insertCountry(country: Country): Long

    fun getCountries(): LiveData<List<Country>>

    suspend fun insertCities(cities: List<City>)

    suspend fun getCitiesByCountryId(countryId: Long): List<City>

    suspend fun deleteAllCountries()
}