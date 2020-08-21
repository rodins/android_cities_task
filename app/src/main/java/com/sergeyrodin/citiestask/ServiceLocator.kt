package com.sergeyrodin.citiestask

import android.content.Context
import androidx.annotation.VisibleForTesting
import androidx.room.Room
import com.sergeyrodin.citiestask.data.source.CitiesDefaultRepository
import com.sergeyrodin.citiestask.data.source.CitiesRepository
import com.sergeyrodin.citiestask.data.source.local.CitiesDatabase
import com.sergeyrodin.citiestask.data.source.local.CitiesLocalDataSource
import com.sergeyrodin.citiestask.data.source.local.ICitiesLocalDataSource
import com.sergeyrodin.citiestask.data.source.remote.CitiesRemoteDataSource
import com.sergeyrodin.citiestask.info.remote.CityInfoRemoteDataSource
import com.sergeyrodin.citiestask.info.service.CityInfoServiceImpl
import com.sergeyrodin.citiestask.info.view.CityInfoPresenter
import com.sergeyrodin.citiestask.info.view.CityInfoServicePresenter

object ServiceLocator {
    private var database: CitiesDatabase? = null

    @Volatile
    var citiesRepository: CitiesRepository? = null
        @VisibleForTesting set

    @Volatile
    var cityInfoPresenter: CityInfoPresenter? = null
        @VisibleForTesting set

    private val lock = Any()

    @VisibleForTesting
    fun resetRepository() {
        synchronized(lock) {
            // Clear all data to avoid test pollution
            database?.apply {
                clearAllTables()
                close()
            }
            database = null
            citiesRepository = null
        }
    }

    @VisibleForTesting
    fun resetCityInfoPresenter() {
        cityInfoPresenter = null
    }

    fun provideCitiesRepository(context: Context): CitiesRepository {
        synchronized(this) {
            return citiesRepository ?: createCitiesRepository(context)
        }
    }

    fun provideCityInfoPresenter(): CityInfoPresenter {
        return cityInfoPresenter ?: CityInfoServicePresenter(CityInfoServiceImpl(
            CityInfoRemoteDataSource()
        ))
    }

    private fun createCitiesRepository(context: Context): CitiesRepository {
        val newRepo = CitiesDefaultRepository(CitiesRemoteDataSource, createCitesLocalDataSource(context))
        citiesRepository = newRepo
        return newRepo
    }

    private fun createCitesLocalDataSource(context: Context): ICitiesLocalDataSource {
        val database = database ?: createDatabase(context)
        return CitiesLocalDataSource(database.citiesDatabaseDao)
    }

    private fun createDatabase(context: Context): CitiesDatabase {
        val result = Room.databaseBuilder(
            context.applicationContext,
            CitiesDatabase::class.java,
            "Cities.db")
            .build()
        database = result
        return result
    }
}