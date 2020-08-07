package com.sergeyrodin.citiestask.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class FakeDataSource(private val countries: MutableList<Country> = mutableListOf(),
                     private val cities: MutableList<City> = mutableListOf()): CitiesDataSource {

    private val countiesLiveData = MutableLiveData<List<Country>>()

    init {
        countiesLiveData.value = countries
    }

    override fun getCountries(): LiveData<List<Country>> {
        return countiesLiveData
    }

    override suspend fun insertCountry(country: Country) {
        countries.add(country)
        countiesLiveData.value = countries
    }

    override suspend fun getCitiesByCountryId(countryId: Int): List<City> {
        return cities.filter {
            it.countryId == countryId
        }
    }

    override suspend fun insertCity(city: City) {
        cities.add(city)
    }
}