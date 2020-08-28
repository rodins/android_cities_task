package com.sergeyrodin.citiestask.data.source.remote

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

object CitiesRemoteDataSource: ICitiesRemoteDataSource {

    private lateinit var countriesMap: Map<String, List<String>>

    override suspend fun getCountriesNames(): Set<String> {
        countriesMap = CitiesApi.retrofitService.getCities()
        return countriesMap.keys
    }

    override fun getCitiesNames(countryName: String): List<String>? {
        return countriesMap[countryName]
    }
}

