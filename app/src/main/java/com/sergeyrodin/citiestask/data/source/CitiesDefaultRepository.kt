package com.sergeyrodin.citiestask.data.source

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sergeyrodin.citiestask.data.source.local.ICitiesLocalDataSource
import com.sergeyrodin.citiestask.data.source.remote.ICitiesRemoteDataSource
import com.sergeyrodin.citiestask.util.wrapEspressoIdlingResource

class CitiesDefaultRepository(
    private val localDataSource: ICitiesLocalDataSource
) : CitiesRepository {
    private val _cities = MutableLiveData<List<City>>()
    override val cities: LiveData<List<City>>
        get() = _cities

    override suspend fun fetchCitiesByCountryId(countryId: Long) {
        wrapEspressoIdlingResource {
            _cities.value = localDataSource.getCitiesByCountryId(countryId)
        }
    }

    override suspend fun insertCities(cities: List<City>) {
        wrapEspressoIdlingResource {
            localDataSource.insertCities(cities)
        }
    }
}