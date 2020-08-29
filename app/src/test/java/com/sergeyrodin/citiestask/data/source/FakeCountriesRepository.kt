package com.sergeyrodin.citiestask.data.source

import androidx.lifecycle.MutableLiveData

class FakeCountriesRepository: CountriesRepository {
    private val countriesList = mutableListOf<Country>()

    private var loading = false
    private var error = false

    private lateinit var json: Map<String, List<String>>

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