package com.sergeyrodin.citiestask.data.source.remote

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

object CitiesRemoteDataSource: ICitiesRemoteDataSource {

    private val _error = MutableLiveData<String>()
    override val error: LiveData<String>
        get() = _error

    private lateinit var countriesMap: Map<String, List<String>>

    override suspend fun getCountriesNames(): Set<String> {
        try{
            _error.value = ""
            countriesMap = CitiesApi.retrofitService.getCities()
            return countriesMap.keys
        }catch(e: Exception) {
            _error.value = "Failure: ${e.message}"
        }
        return setOf()
    }

    override fun getCitiesNames(countryName: String): List<String>? {
        return countriesMap[countryName]
    }
}

