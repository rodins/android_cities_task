package com.sergeyrodin.citiestask.data.source.remote

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class FakeRemoteDataSource(private var countries: Map<String, List<String>> = mutableMapOf()): ICitiesRemoteDataSource {
    private val _error = MutableLiveData<String>()
    override val error: LiveData<String>
        get() = _error

    private val _loading = MutableLiveData<Boolean>()
    override val loading: LiveData<Boolean>
        get() = _loading

    override suspend fun getCountries(): Map<String, List<String>> {
        return countries
    }

    fun setLoading() {
        _loading.value = true
        _error.value = ""
    }

    fun setError() {
        _loading.value = false
        _error.value = "Error"
    }
}