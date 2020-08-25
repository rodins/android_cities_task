package com.sergeyrodin.citiestask

import android.content.Context
import androidx.annotation.VisibleForTesting
import androidx.room.Room
import com.sergeyrodin.citiestask.data.source.CitiesDefaultRepository
import com.sergeyrodin.citiestask.data.source.CitiesRepository
import com.sergeyrodin.citiestask.data.source.CountriesDefaultRepository
import com.sergeyrodin.citiestask.data.source.CountriesRepository
import com.sergeyrodin.citiestask.data.source.local.CitiesDatabase
import com.sergeyrodin.citiestask.data.source.local.CitiesLocalDataSource
import com.sergeyrodin.citiestask.data.source.local.ICitiesLocalDataSource
import com.sergeyrodin.citiestask.data.source.remote.CitiesRemoteDataSource
import com.sergeyrodin.citiestask.info.remote.CityInfoConverterDataSource
import com.sergeyrodin.citiestask.info.remote.GeoNamesRemoteDataSource
import com.sergeyrodin.citiestask.info.interactor.CityInfoInteractor
import com.sergeyrodin.citiestask.info.view.ICityInfoPresenter
import com.sergeyrodin.citiestask.info.view.CityInfoPresenter

object ServiceLocator {
    private var database: CitiesDatabase? = null

    @Volatile
    var citiesRepository: CitiesRepository? = null
        @VisibleForTesting set

    @Volatile
    var countriesRepository: CountriesRepository? = null
        @VisibleForTesting set

    @Volatile
    var cityInfoPresenter: ICityInfoPresenter? = null
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

    fun provideCountriesRepository(context: Context): CountriesRepository{
        synchronized(this) {
            return countriesRepository ?: createCountriesRepository(context)
        }
    }

    fun provideCitiesRepository(context: Context): CitiesRepository {
        synchronized(this) {
            return citiesRepository ?: createCitiesRepository(context)
        }
    }

    fun provideCityInfoPresenter(): ICityInfoPresenter {
        return cityInfoPresenter ?: CityInfoPresenter(CityInfoInteractor(
            CityInfoConverterDataSource(
                GeoNamesRemoteDataSource()
            )
        ))
    }

    private fun createCountriesRepository(context: Context): CountriesRepository {
        val newRepo = CountriesDefaultRepository(CitiesRemoteDataSource, createCitiesLocalDataSource(context))
        countriesRepository = newRepo
        return newRepo
    }

    private fun createCitiesRepository(context: Context): CitiesRepository {
        val newRepo = CitiesDefaultRepository(createCitiesLocalDataSource(context))
        citiesRepository = newRepo
        return newRepo
    }

    private fun createCitiesLocalDataSource(context: Context): ICitiesLocalDataSource {
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