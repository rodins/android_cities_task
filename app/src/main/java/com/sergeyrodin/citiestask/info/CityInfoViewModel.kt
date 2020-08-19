package com.sergeyrodin.citiestask.info

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class CityInfoViewModel(private val dataSource: CityInfoDataSource): ViewModel() {
    val loading = dataSource.loading
    val cityInfo = dataSource.getCityInfo()
    val error = dataSource.error

    fun start(country: String, city: String) {
        viewModelScope.launch {
            dataSource.start(country, city)
        }
    }
}