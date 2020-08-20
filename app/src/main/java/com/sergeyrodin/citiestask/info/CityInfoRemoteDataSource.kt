package com.sergeyrodin.citiestask.info

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import java.lang.Exception

class CityInfoRemoteDataSource: CityInfoDataSource {
    private val _loading = MutableLiveData<Boolean>()
    override val loading: LiveData<Boolean>
        get() = _loading

    private val _cityInfo = MutableLiveData<CityInfo>()
    override fun getCityInfo(): LiveData<CityInfo> {
        return _cityInfo
    }

    private val _error = MutableLiveData<String>()
    override val error: LiveData<String>
        get() = _error

    override suspend fun start(country: String, city: String) {
        try {
            _loading.value = true
            _error.value = ""
            val list = CityInfoApi.retrofitService.getCityInfo(country, city)
            _cityInfo.value = list[0]
        }catch(e: Exception) {
            _error.value = e.localizedMessage
        }finally {
            _loading.value = false
        }
    }
}