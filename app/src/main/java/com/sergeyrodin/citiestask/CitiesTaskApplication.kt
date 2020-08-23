package com.sergeyrodin.citiestask

import android.app.Application
import com.sergeyrodin.citiestask.data.source.CitiesRepository
import com.sergeyrodin.citiestask.info.view.ICityInfoPresenter

class CitiesTaskApplication: Application() {
    val citiesRepository: CitiesRepository
        get() = ServiceLocator.provideCitiesRepository(this)

    val cityInfoPresenter: ICityInfoPresenter
        get() = ServiceLocator.provideCityInfoPresenter()
}