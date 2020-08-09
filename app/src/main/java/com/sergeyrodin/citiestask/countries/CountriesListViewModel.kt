package com.sergeyrodin.citiestask.countries

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sergeyrodin.citiestask.data.source.CitiesRepository

class CountriesListViewModel(repository: CitiesRepository): ViewModel() {

    private val _response = MutableLiveData<String>()
    val response: LiveData<String>
        get() = _response

    init {
        _response.value = "Set the Cities API response here!"
    }

}

class CountriesListViewModelFactory(private val repository: CitiesRepository): ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CountriesListViewModel(repository) as T
    }
}