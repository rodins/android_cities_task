package com.sergeyrodin.citiestask.data.source.remote

object CitiesRemoteDataSource: ICitiesRemoteDataSource {
    override fun getCountries(): Map<String, List<String>> {
        TODO("Not yet implemented")
    }

    override suspend fun getJsonText(): String {
        return try {
            val countries = CitiesApi.retrofitService.getCities()
            countries["Ukraine"]?.get(0)?:""
        }catch(e: Exception) {
            "Failure: ${e.message}"
        }
    }
}