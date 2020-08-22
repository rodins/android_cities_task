package com.sergeyrodin.citiestask.info.remote

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val BASE_URL = "http://api.geonames.org/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

internal interface CityInfoApiService {
    @GET("wikipediaSearchJSON?username=rodins")
    suspend fun getCityInfo(@Query("q") country: String, @Query("title")city: String): GeoNames
}

internal object CityInfoApi{
    val retrofitService: CityInfoApiService by lazy {
        retrofit.create(CityInfoApiService::class.java)
    }
}
