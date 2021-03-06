package com.sergeyrodin.citiestask.data.source

import com.sergeyrodin.citiestask.data.source.local.ICitiesLocalDataSource
import com.sergeyrodin.citiestask.data.source.remote.ICitiesRemoteDataSource
import com.sergeyrodin.citiestask.util.wrapEspressoIdlingResource

class CountriesDefaultRepository(
    private val remoteDataSource: ICitiesRemoteDataSource,
    private val localDataSource: ICitiesLocalDataSource
) : CountriesRepository {

    override suspend fun getCountries(): List<Country> {
        wrapEspressoIdlingResource {
            return localDataSource.getCountriesList()
        }
    }

    override suspend fun deleteAllCountries() {
        wrapEspressoIdlingResource {
            localDataSource.deleteAllCountries()
        }
    }

    override suspend fun loadCountriesAndCitiesToDb() {
        wrapEspressoIdlingResource {
            val countriesNames = remoteDataSource.getCountriesNames()
            if (countriesNames.isNotEmpty()) {
                val countriesToDb = mutableListOf<Country>()
                countriesNames.forEach { countryName ->
                    if (countryName.isNotEmpty()) {
                        val country = Country(name = countryName)
                        countriesToDb.add(country)
                    }
                }
                localDataSource.insertCountries(countriesToDb)
                val countriesFromDb = localDataSource.getCountriesList()
                val citiesToDb = mutableListOf<City>()
                countriesFromDb.forEach { country ->
                    val cities = remoteDataSource.getCitiesNames(country.name)?.filter {
                        it.isNotEmpty()
                    }?.map { cityName ->
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
}