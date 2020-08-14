package com.sergeyrodin.citiestask.data.source.local

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class FakeLocalDataSource(private val countries: MutableList<Country> = mutableListOf(),
                          private val cities: MutableList<City> = mutableListOf()):
    ICitiesLocalDataSource {
    private val countriesLiveData = MutableLiveData<List<Country>>(countries)
    private var countryId = 0L

    init {
        countryId = countries.size.toLong()
    }

    override fun getCountries(): LiveData<List<Country>>{
        return countriesLiveData
    }

    override suspend fun insertCountry(country: Country): Long {
        country.id = ++countryId
        countries.add(country)
        countriesLiveData.value = countries
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

    override suspend fun deleteAllCountries() {
        countries.clear()
        cities.clear()
    }
}