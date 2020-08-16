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

    override suspend fun insertCountries(countries: List<Country>) {
        wrapEspressoIdlingResource {
            return localDataSource.insertCountries(countries)
        }
    }

    override suspend fun insertCities(cities: List<City>) {
        wrapEspressoIdlingResource {
            localDataSource.insertCities(cities)
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
            val countriesToDb = mutableListOf<Country>()
            var countryId = 1L
            countries.keys.forEach { countryName ->
                if(countryName.isNotEmpty()) {
                    val country = Country(countryId, countryName)
                    countriesToDb.add(country)
                    val cities = countries[countryName]?.map { cityName ->
                        City(name = cityName, countryId = countryId)
                    }
                    localDataSource.insertCities(cities?: listOf())
                    countryId++
                }
            }
            localDataSource.insertCountries(countriesToDb)
        }
    }
}