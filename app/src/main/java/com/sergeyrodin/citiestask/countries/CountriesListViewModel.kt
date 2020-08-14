package com.sergeyrodin.citiestask.countries

import androidx.lifecycle.*
import com.sergeyrodin.citiestask.data.source.CitiesRepository
import com.sergeyrodin.citiestask.data.source.local.Country
import com.sergeyrodin.citiestask.data.source.remote.CitiesApi
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CountriesListViewModel(private val repository: CitiesRepository): ViewModel() {

    val loading = repository.loading
    val error = repository.error
    val countries = repository.getCountries()

    fun refresh() {
        viewModelScope.launch {
            repository.deleteAllCountries()
            repository.loadCountriesAndCitiesToDb()
        }
    }
}

class CountriesListViewModelFactory(private val repository: CitiesRepository): ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CountriesListViewModel(repository) as T
    }
}