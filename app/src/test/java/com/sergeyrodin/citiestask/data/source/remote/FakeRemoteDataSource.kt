package com.sergeyrodin.citiestask.data.source.remote

class FakeRemoteDataSource(private var countriesMap: Map<String, List<String>> = mutableMapOf()): ICitiesRemoteDataSource {

    override suspend fun getCountriesNames(): Set<String> {
        return countriesMap.keys
    }

    override fun getCitiesNames(countryName: String): List<String>? {
        return countriesMap[countryName]
    }
}