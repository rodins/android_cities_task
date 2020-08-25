package com.sergeyrodin.citiestask.data.source.remote

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class FakeRemoteDataSource(private var countriesMap: Map<String, List<String>> = mutableMapOf()): ICitiesRemoteDataSource {
    private val _error = MutableLiveData<String>()
    override val error: LiveData<String>
        get() = _error

    override suspend fun getCountriesNames(): Set<String> {
        return countriesMap.keys
    }

    override fun getCitiesNames(countryName: String): List<String>? {
        return countriesMap[countryName]
    }

    fun setError() {
        _error.value = "Error"
    }
}