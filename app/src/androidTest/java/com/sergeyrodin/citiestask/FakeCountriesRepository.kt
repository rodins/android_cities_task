package com.sergeyrodin.citiestask

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sergeyrodin.citiestask.data.source.City
import com.sergeyrodin.citiestask.data.source.CountriesRepository
import com.sergeyrodin.citiestask.data.source.Country

class FakeCountriesRepository: CountriesRepository {
    private val countriesLiveData = MutableLiveData<List<Country>>()
    private val countriesList = mutableListOf<Country>()

    private lateinit var json: Map<String, List<String>>

    private val _loading = MutableLiveData<Boolean>()
    override val loading: LiveData<Boolean>
        get() = _loading

    private val _error = MutableLiveData<String>()
    override val error: LiveData<String>
        get() = _error

    fun addCountries(vararg country: Country) {
        country.forEach {
            countriesList.add(it)
        }
        countriesLiveData.value = countriesList
    }

    fun addJsonMap(json: Map<String, List<String>>) {
        this.json = json
    }

    override fun getCountries(): LiveData<List<Country>> {
        return countriesLiveData
    }

    override suspend fun insertCountries(countries: List<Country>) {
        countriesList.addAll(countries)
        countriesLiveData.value = countries
    }

    override suspend fun loadCountriesAndCitiesToDb() {
        var countryId = countriesList.size.toLong()
        json.keys.forEach {
            val country = Country(++countryId, it)
            countriesList.add(country)
        }
        countriesLiveData.value = countriesList
    }

    override suspend fun deleteAllCountries() {
        countriesList.clear()
    }

    fun errorMode() {
        _loading.value = false
        _error.value = "Error"
    }
}