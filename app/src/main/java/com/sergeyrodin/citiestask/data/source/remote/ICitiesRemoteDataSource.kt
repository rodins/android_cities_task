package com.sergeyrodin.citiestask.data.source.remote

import androidx.lifecycle.LiveData

interface ICitiesRemoteDataSource {
    fun getCountries(): Map<String, List<String>>

    suspend fun getJsonText(): String
}