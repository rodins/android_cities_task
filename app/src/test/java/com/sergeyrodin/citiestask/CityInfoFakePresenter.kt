package com.sergeyrodin.citiestask

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sergeyrodin.citiestask.info.CityInfo
import com.sergeyrodin.citiestask.info.view.CityInfoPresenter

class CityInfoFakePresenter: CityInfoPresenter {
    private val _loading = MutableLiveData<Boolean>()
    override val loading: LiveData<Boolean>
        get() = _loading

    private val _error = MutableLiveData<String>()
    override val error: LiveData<String>
        get() = _error

    private val cityInfoData = CityInfo()
    private val _cityInfo = MutableLiveData<CityInfo>()
    override val cityInfo: LiveData<CityInfo>
        get() = _cityInfo

    override suspend fun fetchCityInfo(country: String, city: String) {
        cityInfoData.title = city
        cityInfoData.summary = country
        cityInfoData.latitude = "1234"
        cityInfoData.longitude = "5678"
        _cityInfo.value = cityInfoData
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