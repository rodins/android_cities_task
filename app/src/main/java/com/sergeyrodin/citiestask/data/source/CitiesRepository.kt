package com.sergeyrodin.citiestask.data.source

import androidx.lifecycle.LiveData

interface CitiesRepository {
    suspend fun insertCities(cities: List<City>)
    suspend fun fetchCitiesByCountryId(countryId: Long): List<City>
}