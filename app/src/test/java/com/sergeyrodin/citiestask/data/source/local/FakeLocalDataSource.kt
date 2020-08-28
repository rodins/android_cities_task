package com.sergeyrodin.citiestask.data.source.local

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sergeyrodin.citiestask.data.source.City
import com.sergeyrodin.citiestask.data.source.Country

class FakeLocalDataSource(private val countriesList: MutableList<Country> = mutableListOf(),
                          private val citiesList: MutableList<City> = mutableListOf()):
    ICitiesLocalDataSource {

    fun addCountries(vararg country: Country) {
        for(item in country) {
            countriesList.add(item)
        }
    }

    fun addCities(vararg city: City) {
        for(item in city) {
            citiesList.add(item)
        }
    }

    override suspend fun getCountriesList(): List<Country> {
        return countriesList
    }

    override suspend fun insertCountries(countries: List<Country>) {
        var countryId = 1L
        countries.forEach { country ->
            country.id = countryId++
        }
        countriesList.addAll(countries)
    }

    override suspend fun getCitiesByCountryId(countryId: Long): List<City> {
        return citiesList.filter {
            it.countryId == countryId
        }
    }

    override suspend fun insertCities(cities: List<City>) {
        if(countriesList.isNotEmpty()) {
            citiesList.addAll(cities)
        }
    }

    override suspend fun deleteAllCountries() {
        countriesList.clear()
        citiesList.clear()
    }
}