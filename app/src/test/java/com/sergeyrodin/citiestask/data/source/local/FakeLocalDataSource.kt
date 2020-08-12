package com.sergeyrodin.citiestask.data.source.local

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class FakeLocalDataSource(private val countries: MutableList<Country> = mutableListOf(),
                          private val cities: MutableList<City> = mutableListOf()):
    ICitiesLocalDataSource {

    private var countryId = 0L

    init {
        countryId = countries.size.toLong()
    }

    override suspend fun getCountries(): List<Country>{
        return countries
    }

    override suspend fun insertCountry(country: Country): Long {
        country.id = ++countryId
        countries.add(country)
        return countryId
    }

    override suspend fun getCitiesByCountryId(countryId: Long): List<City> {
        return cities.filter {
            it.countryId == countryId
        }
    }

    override suspend fun insertCity(city: City) {
        cities.add(city)
    }
}