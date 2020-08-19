package com.sergeyrodin.citiestask.info

import androidx.lifecycle.LiveData

interface CityInfoDataSource {
    val loading: LiveData<Boolean>
    fun getCityInfo(): LiveData<CityInfo>

    fun start(country: String, city: String)
}