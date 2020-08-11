package com.sergeyrodin.citiestask.data.source.remote

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

object CitiesRemoteDataSource: ICitiesRemoteDataSource {

    private val _status = MutableLiveData<String>()
    override val status: LiveData<String>
        get() = _status

    override suspend fun getCountries(): Map<String, List<String>> {
        try{
            _status.value = "Loading..."
            val countries = CitiesApi.retrofitService.getCities()
            _status.value = countries["Ukraine"]?.get(0)?:""
            return countries
        }catch(e: Exception) {
            _status.value = "Failure: ${e.message}"
        }
        return mapOf()
    }
}

