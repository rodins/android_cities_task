package com.sergeyrodin.citiestask.data.source

import androidx.lifecycle.LiveData

interface CitiesRepository {
    val cities: LiveData<List<City>>

    suspend fun insertCities(cities: List<City>)
    suspend fun fetchCitiesByCountryId(countryId: Long)
}