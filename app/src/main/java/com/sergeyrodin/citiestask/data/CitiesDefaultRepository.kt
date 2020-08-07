package com.sergeyrodin.citiestask.data

import androidx.lifecycle.LiveData

class CitiesDefaultRepository(private val remoteDataSource: CitiesDataSource,
                              private val localDataSource: CitiesDataSource) {

    fun getCountries(): LiveData<List<Country>> {
        return localDataSource.getCountries()
    }

    suspend fun getCitiesByCountryId(countryId: Int): List<City> {
        return localDataSource.getCitiesByCountryId(countryId)
    }

    suspend fun loadCountriesAndCitiesToDb() {
        val countries = remoteDataSource.getCountries().value
        countries?.forEach { country ->
            localDataSource.insertCountry(country)
        }
    }
}