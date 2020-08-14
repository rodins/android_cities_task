package com.sergeyrodin.citiestask.data.source

import androidx.lifecycle.LiveData
import com.sergeyrodin.citiestask.data.source.local.City
import com.sergeyrodin.citiestask.data.source.local.Country

interface CitiesRepository {
    val loading: LiveData<Boolean>
    val error: LiveData<String>

    fun getCountries(): LiveData<List<Country>>

    suspend fun insertCountry(country: Country): Long

    suspend fun insertCity(city: City)

    suspend fun getCitiesByCountryId(countryId: Long): List<City>

    suspend fun loadCountriesAndCitiesToDb()

    suspend fun deleteAllCountries()
}