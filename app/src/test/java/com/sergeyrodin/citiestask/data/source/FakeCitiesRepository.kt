package com.sergeyrodin.citiestask.data.source

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class FakeCitiesRepository: CitiesRepository {

    private val citiesList = mutableListOf<City>()
    private val _cities = MutableLiveData<List<City>>()
    override val cities: LiveData<List<City>>
        get() = _cities

    fun addCities(vararg city: City) {
        city.forEach {
            citiesList.add(it)
        }
    }

    override suspend fun fetchCitiesByCountryId(countryId: Long) {
        _cities.value = citiesList.filter {
            it.countryId == countryId
        }
    }

    override suspend fun insertCities(cities: List<City>) {
        citiesList.addAll(cities)
        _cities.value = citiesList
    }
}