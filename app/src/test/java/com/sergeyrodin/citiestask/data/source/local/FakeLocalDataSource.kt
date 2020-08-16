package com.sergeyrodin.citiestask.data.source.local

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class FakeLocalDataSource(private val countriesList: MutableList<Country> = mutableListOf(),
                          private val citiesList: MutableList<City> = mutableListOf()):
    ICitiesLocalDataSource {
    private val countriesLiveData = MutableLiveData<List<Country>>(countriesList)

    override fun getCountries(): LiveData<List<Country>>{
        return countriesLiveData
    }

    override suspend fun insertCountries(countries: List<Country>) {
        countriesList.addAll(countries)
        countriesLiveData.value = countriesList
    }

    override suspend fun getCitiesByCountryId(countryId: Long): List<City> {
        return citiesList.filter {
            it.countryId == countryId
        }
    }

    override suspend fun insertCities(cities: List<City>) {
        citiesList.addAll(cities)
    }

    override suspend fun deleteAllCountries() {
        countriesList.clear()
        citiesList.clear()
    }
}