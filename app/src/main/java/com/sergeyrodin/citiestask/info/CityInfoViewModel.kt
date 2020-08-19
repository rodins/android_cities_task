package com.sergeyrodin.citiestask.info

import androidx.lifecycle.ViewModel

class CityInfoViewModel(private val dataSource: CityInfoDataSource): ViewModel() {
    val loading = dataSource.loading
    val cityInfo = dataSource.getCityInfo()

    fun start(country: String, city: String) {
        dataSource.start(country, city)
    }
}