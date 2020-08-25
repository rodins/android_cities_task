package com.sergeyrodin.citiestask.data.source

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sergeyrodin.citiestask.data.source.local.ICitiesLocalDataSource
import com.sergeyrodin.citiestask.data.source.remote.ICitiesRemoteDataSource
import com.sergeyrodin.citiestask.util.wrapEspressoIdlingResource

class CountriesDefaultRepository(
    private val remoteDataSource: ICitiesRemoteDataSource,
    private val localDataSource: ICitiesLocalDataSource
): CountriesRepository {
    private val _loading = MutableLiveData<Boolean>()
    override val loading: LiveData<Boolean>
        get() = _loading

    override val error = remoteDataSource.error

    override fun getCountries(): LiveData<List<Country>> {
        wrapEspressoIdlingResource {
            return localDataSource.getCountries()
        }
    }

    override suspend fun insertCountries(countries: List<Country>) {
        wrapEspressoIdlingResource {
            return localDataSource.insertCountries(countries)
        }
    }

    override suspend fun deleteAllCountries() {
        wrapEspressoIdlingResource {
            localDataSource.deleteAllCountries()
        }
    }

    override suspend fun loadCountriesAndCitiesToDb() {
        wrapEspressoIdlingResource {
            _loading.value = true
            val countriesNames = remoteDataSource.getCountriesNames()
            if(countriesNames.isNotEmpty()) {
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
                    val cities = remoteDataSource.getCitiesNames(country.name)?.map { cityName ->
                        City(name = cityName, countryId = country.id)
                    }
                    cities?.let {
                        citiesToDb.addAll(it)
                    }
                }
                localDataSource.insertCities(citiesToDb)
            }
            _loading.value = false
        }
    }
}