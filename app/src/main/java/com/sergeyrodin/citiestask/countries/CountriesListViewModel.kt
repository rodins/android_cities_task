package com.sergeyrodin.citiestask.countries

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sergeyrodin.citiestask.data.source.CitiesRepository
import com.sergeyrodin.citiestask.data.source.remote.CitiesApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CountriesListViewModel(repository: CitiesRepository): ViewModel() {

    val response = repository.getJsonText()

}

class CountriesListViewModelFactory(private val repository: CitiesRepository): ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CountriesListViewModel(repository) as T
    }
}