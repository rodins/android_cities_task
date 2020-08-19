package com.sergeyrodin.citiestask.data.source.remote

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class FakeRemoteDataSource(private var countries: Map<String, List<String>> = mutableMapOf()): ICitiesRemoteDataSource {
    private val _error = MutableLiveData<String>()
    override val error: LiveData<String>
        get() = _error

    override suspend fun getCountries(): Map<String, List<String>> {
        return countries
    }

    fun setError() {
        _error.value = "Error"
    }
}