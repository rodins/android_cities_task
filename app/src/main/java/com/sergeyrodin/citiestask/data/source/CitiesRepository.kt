package com.sergeyrodin.citiestask.data.source

import androidx.lifecycle.LiveData
import com.sergeyrodin.citiestask.data.source.local.City
import com.sergeyrodin.citiestask.data.source.local.Country

interface CitiesRepository {
    val loading: LiveData<Boolean>
    val error: LiveData<String>

    suspend fun getCountries(): List<Country>

    suspend fun getCitiesByCountryId(countryId: Long): List<City>

    suspend fun loadCountriesAndCitiesToDb()
}