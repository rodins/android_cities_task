package com.sergeyrodin.citiestask.info.interactor

import com.sergeyrodin.citiestask.info.CityInfo

interface ICityInfoInteractor {
    suspend fun fetchCityInfo(country: String, city: String): CityInfo
}