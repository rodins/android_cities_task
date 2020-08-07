package com.sergeyrodin.citiestask.data

import androidx.lifecycle.LiveData
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CitiesLocalDataSource(private val databaseDao: CitiesDatabaseDao,
                            private val dispatcher: CoroutineDispatcher = Dispatchers.IO ) :
    CitiesDataSource {

    override suspend fun insertCountry(country: Country) {
        withContext(dispatcher) {
            databaseDao.insertCountry(country)
        }
    }

    override fun getCountries(): LiveData<List<Country>> {
        return databaseDao.getCountries()
    }

    override suspend fun insertCity(city: City) {
        withContext(dispatcher) {
            databaseDao.insertCity(city)
        }
    }

    override suspend fun getCitiesByCountryId(countryId: Int): List<City> {
        var output = listOf<City>()
        withContext(dispatcher) {
           output = databaseDao.getCitiesByCountryId(countryId)
        }
        return output
    }
}