package com.sergeyrodin.citiestask.data.source

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sergeyrodin.citiestask.data.source.local.ICitiesLocalDataSource
import com.sergeyrodin.citiestask.data.source.remote.ICitiesRemoteDataSource
import com.sergeyrodin.citiestask.util.wrapEspressoIdlingResource

class CitiesDefaultRepository(
    private val remoteDataSource: ICitiesRemoteDataSource,
    private val localDataSource: ICitiesLocalDataSource
) : CitiesRepository {
    private val _loading = MutableLiveData<Boolean>()
    override val loading: LiveData<Boolean>
        get() = _loading

    private val _cities = MutableLiveData<List<City>>()
    override val cities: LiveData<List<City>>
        get() = _cities

    override val error = remoteDataSource.error

    override fun getCountries(): LiveData<List<Country>> {
        wrapEspressoIdlingResource {
            return localDataSource.getCountries()
        }
    }

    override suspend fun fetchCitiesByCountryId(countryId: Long) {
        wrapEspressoIdlingResource {
            if(_cities.value == null) {
                _cities.value = localDataSource.getCitiesByCountryId(countryId)
            }
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
            _loading.value = true
            val countries = remoteDataSource.getCountries()
            if(countries.isNotEmpty()) {
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
            _loading.value = false
        }
    }
}