package com.sergeyrodin.citiestask.data.source

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sergeyrodin.citiestask.data.source.local.ICitiesLocalDataSource
import com.sergeyrodin.citiestask.data.source.remote.ICitiesRemoteDataSource
import com.sergeyrodin.citiestask.util.wrapEspressoIdlingResource

class CitiesDefaultRepository(
    private val localDataSource: ICitiesLocalDataSource
) : CitiesRepository {

    override suspend fun fetchCitiesByCountryId(countryId: Long): List<City> {
        wrapEspressoIdlingResource {
            return localDataSource.getCitiesByCountryId(countryId)
        }
    }
}