package com.sergeyrodin.citiestask.data.source.remote

import androidx.lifecycle.LiveData

interface ICitiesRemoteDataSource {
    val status: LiveData<String>

    suspend fun getCountries(): Map<String, List<String>>
}