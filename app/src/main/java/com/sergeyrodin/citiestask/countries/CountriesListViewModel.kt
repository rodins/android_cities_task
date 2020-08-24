package com.sergeyrodin.citiestask.countries

import androidx.lifecycle.*
import com.sergeyrodin.citiestask.data.source.CitiesRepository
import kotlinx.coroutines.launch

class CountriesListViewModel(private val repository: CitiesRepository) : ViewModel() {

    val loading = repository.loading
    val error = repository.error
    val countries = repository.getCountries()

    fun loadCountries() {
        viewModelScope.launch {
            repository.loadCountriesAndCitiesToDb()
        }
    }

    fun refresh() {
        viewModelScope.launch {
            repository.deleteAllCountries()
            repository.loadCountriesAndCitiesToDb()
        }
    }
}

class CountriesListViewModelFactory(private val repository: CitiesRepository) :
    ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CountriesListViewModel(repository) as T
    }
}