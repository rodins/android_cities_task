package com.sergeyrodin.citiestask.data.source.remote

class FakeRemoteDataSource(private var countries: List<CountryRemote> = mutableListOf()): ICitiesRemoteDataSource {
    override fun getCountries(): List<CountryRemote> {
        return countries
    }
}