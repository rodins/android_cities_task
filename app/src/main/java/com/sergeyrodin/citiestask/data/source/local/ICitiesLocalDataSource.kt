package com.sergeyrodin.citiestask.data.source.local

import androidx.lifecycle.LiveData
import com.sergeyrodin.citiestask.data.source.local.City
import com.sergeyrodin.citiestask.data.source.local.Country

interface ICitiesLocalDataSource {
    suspend fun insertCountry(country: Country): Long

    suspend fun getCountries(): List<Country>

    suspend fun insertCity(city: City)

    suspend fun getCitiesByCountryId(countryId: Long): List<City>
}