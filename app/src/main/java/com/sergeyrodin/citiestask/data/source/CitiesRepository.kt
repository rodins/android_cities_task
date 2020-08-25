package com.sergeyrodin.citiestask.data.source

import androidx.lifecycle.LiveData

interface CitiesRepository {
    val loading: LiveData<Boolean>
    val error: LiveData<String>

    val cities: LiveData<List<City>>
    fun getCountries(): LiveData<List<Country>>

    suspend fun insertCountries(countries: List<Country>)

    suspend fun insertCities(cities: List<City>)

    suspend fun fetchCitiesByCountryId(countryId: Long)

    suspend fun loadCountriesAndCitiesToDb()

    suspend fun deleteAllCountries()
}