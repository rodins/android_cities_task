package com.sergeyrodin.citiestask.data.source

import androidx.lifecycle.LiveData
import com.sergeyrodin.citiestask.data.source.local.City
import com.sergeyrodin.citiestask.data.source.local.Country
import com.sergeyrodin.citiestask.data.source.local.ICitiesLocalDataSource
import com.sergeyrodin.citiestask.data.source.remote.ICitiesRemoteDataSource
import com.sergeyrodin.citiestask.util.wrapEspressoIdlingResource

class CitiesDefaultRepository(private val remoteDataSource: ICitiesRemoteDataSource,
                              private val localDataSource: ICitiesLocalDataSource
) : CitiesRepository {
    override val loading = remoteDataSource.loading
    override val error = remoteDataSource.error

    override fun getCountries(): LiveData<List<Country>> {
        wrapEspressoIdlingResource {
            return localDataSource.getCountries()
        }
    }

    override suspend fun getCitiesByCountryId(countryId: Long): List<City> {
        wrapEspressoIdlingResource {
            return localDataSource.getCitiesByCountryId(countryId)
        }
    }

    override suspend fun insertCountry(country: Country): Long {
        wrapEspressoIdlingResource {
            return localDataSource.insertCountry(country)
        }
    }

    override suspend fun insertCity(city: City) {
        wrapEspressoIdlingResource {
            localDataSource.insertCity(city)
        }
    }

    override suspend fun deleteAllCountries() {
        wrapEspressoIdlingResource {
            localDataSource.deleteAllCountries()
        }
    }

    override suspend fun loadCountriesAndCitiesToDb() {
        wrapEspressoIdlingResource {
            val countries = remoteDataSource.getCountries()
            countries.keys.forEach { countryName ->
                if(countryName.isNotEmpty()) {
                    val country = Country(name = countryName)
                    val countryId = localDataSource.insertCountry(country)
                    countries[countryName]?.forEach { cityName ->
                        val city = City(name = cityName, countryId = countryId)
                        localDataSource.insertCity(city)
                    }
                }
            }
        }
    }
}