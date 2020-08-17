package com.sergeyrodin.citiestask.data.source.remote

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

object CitiesRemoteDataSource: ICitiesRemoteDataSource {

    private val _error = MutableLiveData<String>()
    override val error: LiveData<String>
        get() = _error

    override suspend fun getCountries(): Map<String, List<String>> {
        try{
            _error.value = ""
            return CitiesApi.retrofitService.getCities()
        }catch(e: Exception) {
            _error.value = "Failure: ${e.message}"
        }
        return mapOf()
    }
}

