package com.sergeyrodin.citiestask

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sergeyrodin.citiestask.data.source.CitiesRepository
import com.sergeyrodin.citiestask.data.source.local.City
import com.sergeyrodin.citiestask.data.source.local.Country

class FakeTestRepository: CitiesRepository {
    private val countriesLiveData = MutableLiveData<List<Country>>()
    private val countries = mutableListOf<Country>()
    private val cities = mutableListOf<City>()

    private val _error = MutableLiveData<String>()
    override val error: LiveData<String>
        get() = _error

    private val _loading = MutableLiveData<Boolean>()
    override val loading: LiveData<Boolean>
        get() = _loading

    fun loadingMode() {
        _loading.value = true
        _error.value = ""
    }

    fun errorMode() {
        _loading.value = false
        _error.value = "Error"
    }

    fun addCountries(vararg country: Country) {
        country.forEach {
            countries.add(it)
        }
        countriesLiveData.value = countries
    }

    fun addCities(vararg city: City) {
        city.forEach {
            cities.add(it)
        }
    }

    override fun getCountries(): LiveData<List<Country>> {
        return countriesLiveData
    }

    override suspend fun getCitiesByCountryId(countryId: Long): List<City> {
        _loading.value = false
        _error.value = ""
        return cities.filter {
            it.countryId == countryId
        }
    }

    override suspend fun insertCity(city: City) {
        cities.add(city)
    }

    override suspend fun insertCountry(country: Country): Long {
        countries.add(country)
        countriesLiveData.value = countries
        return country.id
    }

    override suspend fun loadCountriesAndCitiesToDb() {
        TODO("Not yet implemented")
    }
}