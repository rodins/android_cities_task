package com.sergeyrodin.citiestask.data.source.remote

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object CitiesRemoteDataSource: ICitiesRemoteDataSource {
    override fun getCountries(): Map<String, List<String>> {
        TODO("Not yet implemented")
    }

    override suspend fun getJsonText(): String {
        val getCountriesDeferred = CitiesApi.retrofitService.getCities()
        try {
            val countries = getCountriesDeferred.await()
            return countries.get("Ukraine")?.get(0)?:""
        }catch(e: Exception) {
            return "Failure: ${e.message}"
        }
    }
}