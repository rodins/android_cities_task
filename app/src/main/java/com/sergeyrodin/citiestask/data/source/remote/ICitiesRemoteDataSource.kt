package com.sergeyrodin.citiestask.data.source.remote

import androidx.lifecycle.LiveData

interface ICitiesRemoteDataSource {
    suspend fun getCountriesNames(): Set<String>
    fun getCitiesNames(countryName: String): List<String>?
}