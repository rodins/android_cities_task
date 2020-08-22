package com.sergeyrodin.citiestask.info.remote

interface GeoNamesDataSource {
    suspend fun fetchGeoNames(country: String, city: String): GeoNames
}