package com.sergeyrodin.citiestask.cities

import androidx.lifecycle.*
import com.sergeyrodin.citiestask.data.source.CitiesRepository
import com.sergeyrodin.citiestask.data.source.City
import kotlinx.coroutines.launch

class CitiesListViewModel(private val repository: CitiesRepository): ViewModel() {
    private val _cities = MutableLiveData<List<City>>()
    val cities: LiveData<List<City>>
        get() = _cities

    fun start(countryId: Long) {
        if(_cities.value == null) {
            viewModelScope.launch {
                _cities.value = repository.fetchCitiesByCountryId(countryId)
            }
        }
    }
}

class CitiesListViewModelFactory(private val repository: CitiesRepository): ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CitiesListViewModel(repository) as T
    }
}