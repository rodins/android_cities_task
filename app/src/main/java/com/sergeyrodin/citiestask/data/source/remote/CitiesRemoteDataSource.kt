package com.sergeyrodin.citiestask.data.source.remote

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object CitiesRemoteDataSource: ICitiesRemoteDataSource {
    override fun getCountries(): Map<String, List<String>> {
        TODO("Not yet implemented")
    }

    override fun getJsonText(): LiveData<String> {
        val output = MutableLiveData<String>()

        CitiesApi.retrofitService.getCities().enqueue( object: Callback<Map<String, List<String>>> {
            override fun onFailure(call: Call<Map<String, List<String>>>, t: Throwable) {
                output.value = "Failure: " + t.message
            }

            override fun onResponse(call: Call<Map<String, List<String>>>, response: Response<Map<String, List<String>>>) {
                response.body()?.let{ countries ->
                    output.value = countries.get("Ukraine")?.get(0)
                }
                //output.value = "Success: ${response.body()?.size} countries retrieved"
            }
        })

        return output
    }
}