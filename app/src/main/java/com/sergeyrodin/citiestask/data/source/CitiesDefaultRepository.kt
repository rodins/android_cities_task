package com.sergeyrodin.citiestask.data.source

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sergeyrodin.citiestask.data.source.local.City
import com.sergeyrodin.citiestask.data.source.local.Country
import com.sergeyrodin.citiestask.data.source.local.ICitiesLocalDataSource
import com.sergeyrodin.citiestask.data.source.remote.ICitiesRemoteDataSource

class CitiesDefaultRepository(private val remoteDataSource: ICitiesRemoteDataSource,
                              private val localDataSource: ICitiesLocalDataSource
) : CitiesRepository {
    override val status = remoteDataSource.status

    override suspend fun getCountries(): List<Country> {
        val countries = localDataSource.getCountries()
        if(countries.isEmpty()) {
            remoteDataSource.getCountries()
        }
        return countries
    }

    override suspend fun getCitiesByCountryId(countryId: Long): List<City> {
        return localDataSource.getCitiesByCountryId(countryId)
    }

    override suspend fun loadCountriesAndCitiesToDb() {
        val countries = remoteDataSource.getCountries()
        countries.keys.forEach { countryName ->
            val country = Country(name = countryName)
            val countryId = localDataSource.insertCountry(country)
            countries[countryName]?.forEach { cityName ->
                val city = City(name = cityName, countryId = countryId)
                localDataSource.insertCity(city)
            }
        }
    }
}