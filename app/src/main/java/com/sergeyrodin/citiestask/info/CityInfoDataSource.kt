package com.sergeyrodin.citiestask.info

import androidx.lifecycle.LiveData

interface CityInfoDataSource {
    fun getCityInfo(): LiveData<CityInfo>

    fun start(country: String, city: String)
}