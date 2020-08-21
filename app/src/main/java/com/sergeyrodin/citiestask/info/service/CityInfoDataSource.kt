package com.sergeyrodin.citiestask.info.service

import com.sergeyrodin.citiestask.info.CityInfo

interface CityInfoDataSource {
    suspend fun fetchCityInfo(country: String, city: String): CityInfo
}