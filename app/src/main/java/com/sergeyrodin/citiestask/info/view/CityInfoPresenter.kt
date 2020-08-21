package com.sergeyrodin.citiestask.info.view

import androidx.lifecycle.LiveData
import com.sergeyrodin.citiestask.info.CityInfo

interface CityInfoPresenter {
    val loading: LiveData<Boolean>
    val cityInfo: LiveData<CityInfo>
    val error: LiveData<String>

    suspend fun fetchCityInfo(country: String, city: String)
}
