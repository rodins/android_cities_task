package com.sergeyrodin.citiestask.info.remote

class GeoNamesFakeDataSource: GeoNamesDataSource {
    private val geoNamesList = mutableListOf<GeonamesItem>()

    fun addGeoNames(vararg geonames: GeonamesItem) {
        for(item in geonames) {
            geoNamesList.add(item)
        }
    }

    override suspend fun fetchGeoNames(country: String, city: String): GeoNames {
        return GeoNames(geoNamesList)
    }
}