package com.sergeyrodin.citiestask.data.source

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class FakeCitiesRepository: CitiesRepository {

    private val citiesList = mutableListOf<City>()

    fun addCities(vararg city: City) {
        city.forEach {
            citiesList.add(it)
        }
    }

    override suspend fun fetchCitiesByCountryId(countryId: Long): List<City> {
        return citiesList.filter {
            it.countryId == countryId
        }
    }

    override suspend fun insertCities(cities: List<City>) {
        citiesList.addAll(cities)
    }
}