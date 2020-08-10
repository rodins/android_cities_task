package com.sergeyrodin.citiestask.data.source

import androidx.lifecycle.LiveData
import com.sergeyrodin.citiestask.data.source.local.City
import com.sergeyrodin.citiestask.data.source.local.Country

interface CitiesRepository {
    fun getCountries(): LiveData<List<Country>>

    suspend fun getJsonText(): String

    suspend fun getCitiesByCountryId(countryId: Long): List<City>

    suspend fun loadCountriesAndCitiesToDb()
}