package com.sergeyrodin.citiestask.data.source.remote

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET

private const val BASE_URL = "https://raw.githubusercontent.com/"

private val retrofit = Retrofit.Builder()
    .addConverterFactory(ScalarsConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()

interface CitiesApiService {
    @GET("David-Haim/CountriesToCitiesJSON/master/countriesToCities.json")
    fun getCities(): Call<String>
}

object CitiesApi {
    val retrofitService: CitiesApiService by lazy {
        retrofit.create(CitiesApiService::class.java)
    }
}