package com.sergeyrodin.citiestask.data.source.remote

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

private const val BASE_URL = "https://raw.githubusercontent.com/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface CitiesApiService {
    @GET("David-Haim/CountriesToCitiesJSON/master/countriesToCities.json")
    fun getCities(): Call<Map<String, List<String>>>
}

object CitiesApi {
    val retrofitService: CitiesApiService by lazy {
        retrofit.create(CitiesApiService::class.java)
    }
}