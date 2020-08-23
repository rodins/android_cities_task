package com.sergeyrodin.citiestask.info.interactor

import com.sergeyrodin.citiestask.info.CityInfo
import com.sergeyrodin.citiestask.info.remote.CityInfoDataSource

internal class CityInfoInteractor(private val dataSource: CityInfoDataSource): ICityInfoInteractor {
    override suspend fun fetchCityInfo(country: String, city: String): CityInfo {
        return dataSource.fetchCityInfo(country, city)
    }
}
