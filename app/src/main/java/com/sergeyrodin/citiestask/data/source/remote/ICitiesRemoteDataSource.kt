package com.sergeyrodin.citiestask.data.source.remote

interface ICitiesRemoteDataSource {
    fun getCountries(): List<CountryRemote>
}