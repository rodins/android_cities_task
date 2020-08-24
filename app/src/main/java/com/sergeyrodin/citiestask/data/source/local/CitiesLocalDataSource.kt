package com.sergeyrodin.citiestask.data.source.local

import androidx.lifecycle.LiveData
import com.sergeyrodin.citiestask.data.source.City
import com.sergeyrodin.citiestask.data.source.Country
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CitiesLocalDataSource(private val databaseDao: CitiesDatabaseDao,
                            private val dispatcher: CoroutineDispatcher = Dispatchers.IO ) :
    ICitiesLocalDataSource {

    override suspend fun insertCountries(countries: List<Country>) = withContext(dispatcher){
        return@withContext databaseDao.insertCountries(countries)
    }

    override fun getCountries(): LiveData<List<Country>>{
        return databaseDao.getCountries()
    }

    override suspend fun getCountriesList(): List<Country>  = withContext(dispatcher){
        return@withContext databaseDao.getCountriesList()
    }

    override suspend fun insertCities(cities: List<City>) = withContext(dispatcher) {
        databaseDao.insertCity(cities)
    }

    override suspend fun getCitiesByCountryId(countryId: Long): List<City> = withContext(dispatcher) {
        return@withContext databaseDao.getCitiesByCountryId(countryId)
    }

    override suspend fun deleteAllCountries() = withContext(dispatcher){
        databaseDao.deleteAllCities()
        databaseDao.deleteAllCountries()
    }
}