package com.sergeyrodin.citiestask.info

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class CityInfoFakeDataSource: CityInfoDataSource {
    private val cityInfo = CityInfo()
    private val cityInfoLiveData = MutableLiveData<CityInfo>()

    override fun start(country: String, city: String) {
        cityInfo.title = city
        cityInfo.summary = country
        cityInfoLiveData.value = cityInfo
    }

    override fun getCityInfo(): LiveData<CityInfo> {
        return cityInfoLiveData
    }
}