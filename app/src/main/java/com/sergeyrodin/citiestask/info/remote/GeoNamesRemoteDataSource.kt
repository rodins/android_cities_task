package com.sergeyrodin.citiestask.info.remote

internal class GeoNamesRemoteDataSource: GeoNamesDataSource {
    override suspend fun fetchGeoNames(country: String, city: String): GeoNames {
        return CityInfoApi.retrofitService.getCityInfo(country, city)
    }
}