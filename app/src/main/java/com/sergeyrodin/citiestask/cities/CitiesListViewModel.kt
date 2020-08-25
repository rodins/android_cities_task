package com.sergeyrodin.citiestask.cities

import androidx.lifecycle.*
import com.sergeyrodin.citiestask.data.source.CitiesRepository
import com.sergeyrodin.citiestask.data.source.City
import kotlinx.coroutines.launch

class CitiesListViewModel(private val repository: CitiesRepository): ViewModel() {
    val cities = repository.cities

    fun start(countryId: Long) {
        viewModelScope.launch {
            repository.fetchCitiesByCountryId(countryId)
        }
    }
}

class CitiesListViewModelFactory(private val repository: CitiesRepository): ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CitiesListViewModel(repository) as T
    }
}