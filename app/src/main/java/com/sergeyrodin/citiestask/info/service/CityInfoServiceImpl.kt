package com.sergeyrodin.citiestask.info.service

import com.sergeyrodin.citiestask.info.CityInfo

class CityInfoServiceImpl(private val dataSource: CityInfoDataSource): CityInfoService {
    override suspend fun fetchCityInfo(country: String, city: String): CityInfo {
        return dataSource.fetchCityInfo(country, city)
    }
}