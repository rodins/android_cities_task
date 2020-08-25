package com.sergeyrodin.citiestask.data.source.remote

import androidx.lifecycle.LiveData

interface ICitiesRemoteDataSource {
    val error: LiveData<String>

    suspend fun getCountriesNames(): Set<String>
    fun getCitiesNames(countryName: String): List<String>?
}