package com.sergeyrodin.citiestask.info.remote

import com.sergeyrodin.citiestask.info.CityInfo

class CityInfoConverterDataSource(private val dataSource: GeoNamesDataSource): CityInfoDataSource {
    override suspend fun fetchCityInfo(country: String, city: String): CityInfo {
        val geonames = dataSource.fetchGeoNames(country, city)
        val item = geonames.geoNames.find{
            it.title == city
        }?:geonames.geoNames.find {
            it.feature == "city"
        }?:geonames.geoNames.find{
            it.title.indexOf(city) == 0
        }
        return item?.let{
            CityInfo(it.title, it.summary, it.latitude, it.longitude, it.thumbnailImg)
        }?:CityInfo()
    }
}