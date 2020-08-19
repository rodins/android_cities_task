package com.sergeyrodin.citiestask.info

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class CityInfoFakeDataSource: CityInfoDataSource {
    private val _loading = MutableLiveData<Boolean>()
    override val loading: LiveData<Boolean>
        get() = _loading

    private val _error = MutableLiveData<String>()
    override val error: LiveData<String>
        get() = _error

    private val cityInfo = CityInfo()
    private val cityInfoLiveData = MutableLiveData<CityInfo>()

    override suspend fun start(country: String, city: String) {
        cityInfo.title = city
        cityInfo.summary = country
        cityInfo.latitude = "1234"
        cityInfo.longitude = "5678"
        cityInfoLiveData.value = cityInfo
    }

    override fun getCityInfo(): LiveData<CityInfo> {
        return cityInfoLiveData
    }

    fun dataMode() {
        _loading.value = false
        _error.value = ""
    }

    fun loadingMode() {
        _loading.value = true
        _error.value = ""
    }

    fun errorMode() {
        _error.value = "Error"
        _loading.value = false
    }
}