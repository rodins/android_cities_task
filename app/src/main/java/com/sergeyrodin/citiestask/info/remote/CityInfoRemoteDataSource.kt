package com.sergeyrodin.citiestask.info.remote

import com.sergeyrodin.citiestask.info.CityInfo

internal class CityInfoRemoteDataSource: CityInfoDataSource {
    override suspend fun fetchCityInfo(country: String, city: String): CityInfo {
        val geoNames = CityInfoApi.retrofitService.getCityInfo(country, city)
        return geoNames.geoNames[0]
    }
}