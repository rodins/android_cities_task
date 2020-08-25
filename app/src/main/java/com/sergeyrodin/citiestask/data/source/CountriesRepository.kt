package com.sergeyrodin.citiestask.data.source

import androidx.lifecycle.LiveData

interface CountriesRepository {
    val loading: LiveData<Boolean>
    val error: LiveData<String>

    fun getCountries(): LiveData<List<Country>>
    suspend fun insertCountries(countries: List<Country>)
    suspend fun loadCountriesAndCitiesToDb()
    suspend fun deleteAllCountries()
}