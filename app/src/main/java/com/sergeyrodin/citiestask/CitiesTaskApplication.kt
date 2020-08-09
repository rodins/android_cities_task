package com.sergeyrodin.citiestask

import android.app.Application
import com.sergeyrodin.citiestask.data.source.CitiesRepository

class CitiesTaskApplication: Application() {
    val citiesRepository: CitiesRepository
        get() = ServiceLocator.provideCitiesRepository(this)
}