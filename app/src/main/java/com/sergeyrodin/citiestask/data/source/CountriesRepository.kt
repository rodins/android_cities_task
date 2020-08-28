package com.sergeyrodin.citiestask.data.source

import androidx.lifecycle.LiveData

interface CountriesRepository {
    suspend fun getCountries(): List<Country>
    suspend fun insertCountries(countries: List<Country>)
    suspend fun loadCountriesAndCitiesToDb()
    suspend fun deleteAllCountries()
}