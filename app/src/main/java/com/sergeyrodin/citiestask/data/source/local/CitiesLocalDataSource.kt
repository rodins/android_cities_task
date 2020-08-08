package com.sergeyrodin.citiestask.data.source.local

import androidx.lifecycle.LiveData
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CitiesLocalDataSource(private val databaseDao: CitiesDatabaseDao,
                            private val dispatcher: CoroutineDispatcher = Dispatchers.IO ) :
    ICitiesLocalDataSource {

    override suspend fun insertCountry(country: Country): Long = withContext(dispatcher){
        return@withContext databaseDao.insertCountry(country)
    }

    override fun getCountries(): LiveData<List<Country>> {
        return databaseDao.getCountries()
    }

    override suspend fun insertCity(city: City) = withContext(dispatcher) {
        databaseDao.insertCity(city)
    }

    override suspend fun getCitiesByCountryId(countryId: Long): List<City> = withContext(dispatcher) {
        return@withContext databaseDao.getCitiesByCountryId(countryId)
    }
}