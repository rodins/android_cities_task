package com.sergeyrodin.citiestask.info

import androidx.lifecycle.LiveData

class CityInfoRemoteDataSource: CityInfoDataSource {
    override val loading: LiveData<Boolean>
        get() = TODO("Not yet implemented")

    override fun getCityInfo(): LiveData<CityInfo> {
        TODO("Not yet implemented")
    }

    override val error: LiveData<String>
        get() = TODO("Not yet implemented")

    override suspend fun start(country: String, city: String) {
        TODO("Not yet implemented")
    }
}