package com.sergeyrodin.citiestask

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sergeyrodin.citiestask.data.source.CitiesRepository
import com.sergeyrodin.citiestask.data.source.local.City
import com.sergeyrodin.citiestask.data.source.local.Country

class FakeTestRepository: CitiesRepository {
    private val countriesLiveData = MutableLiveData<List<Country>>()
    private val countriesList = mutableListOf<Country>()
    private val citiesList = mutableListOf<City>()

    private lateinit var json: Map<String, List<String>>

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
            countriesList.add(it)
        }
        countriesLiveData.value = countriesList
    }

    fun addCities(vararg city: City) {
        city.forEach {
            citiesList.add(it)
        }
    }

    fun addJsonMap(json: Map<String, List<String>>) {
        this.json = json
    }

    override fun getCountries(): LiveData<List<Country>> {
        return countriesLiveData
    }

    override suspend fun getCitiesByCountryId(countryId: Long): List<City> {
        _loading.value = false
        _error.value = ""
        return citiesList.filter {
            it.countryId == countryId
        }
    }

    override suspend fun insertCities(cities: List<City>) {
        citiesList.addAll(cities)
    }

    override suspend fun insertCountries(countries: List<Country>) {
        countriesList.addAll(countries)
        countriesLiveData.value = countries
    }

    override suspend fun loadCountriesAndCitiesToDb() {
        var countryId = countriesList.size.toLong()
        var cityId = citiesList.size
        json.keys.forEach {
            val country = Country(++countryId, it)
            countriesList.add(country)
            json[it]?.forEach {
                val city = City(++cityId, it, country.id)
                citiesList.add(city)
            }
        }
        countriesLiveData.value = countriesList
    }

    override suspend fun deleteAllCountries() {
        countriesList.clear()
        citiesList.clear()
    }
}