package com.sergeyrodin.citiestask.data.source.remote

import androidx.lifecycle.LiveData

interface ICitiesRemoteDataSource {
    val loading: LiveData<Boolean>
    val error: LiveData<String>

    suspend fun getCountries(): Map<String, List<String>>
}