package com.sergeyrodin.citiestask.data.source.local

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class FakeLocalDataSource(private val countries: MutableList<Country> = mutableListOf(),
                          private val citiesList: MutableList<City> = mutableListOf()):
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
        return citiesList.filter {
            it.countryId == countryId
        }
    }

    override suspend fun insertCities(cities: List<City>) {
        citiesList.addAll(cities)
    }

    override suspend fun deleteAllCountries() {
        countries.clear()
        citiesList.clear()
    }
}