package com.sergeyrodin.citiestask.data

import androidx.lifecycle.LiveData

interface CitiesDataSource {
    suspend fun insertCountry(country: Country)

    fun getCountries(): LiveData<List<Country>>

    suspend fun insertCity(city: City)

    suspend fun getCitiesByCountryId(countryId: Int): List<City>
}