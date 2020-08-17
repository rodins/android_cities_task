package com.sergeyrodin.citiestask.data.source

import android.util.Log
import androidx.lifecycle.LiveData
import com.sergeyrodin.citiestask.data.source.local.City
import com.sergeyrodin.citiestask.data.source.local.Country
import com.sergeyrodin.citiestask.data.source.local.ICitiesLocalDataSource
import com.sergeyrodin.citiestask.data.source.remote.ICitiesRemoteDataSource
import com.sergeyrodin.citiestask.util.wrapEspressoIdlingResource

class CitiesDefaultRepository(
    private val remoteDataSource: ICitiesRemoteDataSource,
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
            val citiesToDb = mutableListOf<City>()
            countries.keys.forEach { countryName ->
                if (countryName.isNotEmpty()) {
                    val country = Country(name = countryName)
                    countriesToDb.add(country)
                }
            }
            localDataSource.insertCountries(countriesToDb)
            val countriesFromDb = localDataSource.getCountriesList()
            countriesFromDb.forEach { country ->
                val cities = countries[country.name]?.map { cityName ->
                    City(name = cityName, countryId = country.id)
                }
                cities?.let {
                    citiesToDb.addAll(it)
                }
            }
            localDataSource.insertCities(citiesToDb)
        }
    }
}