package com.sergeyrodin.citiestask

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sergeyrodin.citiestask.data.source.City
import com.sergeyrodin.citiestask.data.source.CountriesRepository
import com.sergeyrodin.citiestask.data.source.Country

class FakeCountriesRepository: CountriesRepository {
    private val countriesList = mutableListOf<Country>()
    private lateinit var json: Map<String, List<String>>

    private var error = false

    fun addCountries(vararg country: Country) {
        country.forEach {
            countriesList.add(it)
        }
    }

    fun addJsonMap(json: Map<String, List<String>>) {
        this.json = json
    }

    override suspend fun getCountries(): List<Country> {
        return countriesList
    }

    override suspend fun insertCountries(countries: List<Country>) {
        countriesList.addAll(countries)
    }

    override suspend fun loadCountriesAndCitiesToDb() {
        if(error) {
            throw Exception("Error")
        }
        var countryId = countriesList.size.toLong()
        json.keys.forEach {
            val country = Country(++countryId, it)
            countriesList.add(country)
        }
    }

    override suspend fun deleteAllCountries() {
        countriesList.clear()
    }

    fun errorMode() {
        error = true
    }
}