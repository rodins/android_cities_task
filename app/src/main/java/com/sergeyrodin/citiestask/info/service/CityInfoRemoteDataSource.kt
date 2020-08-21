package com.sergeyrodin.citiestask.info.service

import com.sergeyrodin.citiestask.info.CityInfo

class CityInfoRemoteDataSource: CityInfoDataSource {
    override suspend fun fetchCityInfo(country: String, city: String): CityInfo {
        val geoNames = CityInfoApi.retrofitService.getCityInfo(country, city)
        return geoNames.geoNames[0]
    }
}