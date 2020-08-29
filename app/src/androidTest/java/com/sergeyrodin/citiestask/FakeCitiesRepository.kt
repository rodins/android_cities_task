package com.sergeyrodin.citiestask

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sergeyrodin.citiestask.data.source.CitiesRepository
import com.sergeyrodin.citiestask.data.source.City
import com.sergeyrodin.citiestask.data.source.Country

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
}