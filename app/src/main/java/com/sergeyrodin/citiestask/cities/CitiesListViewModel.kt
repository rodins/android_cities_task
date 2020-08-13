package com.sergeyrodin.citiestask.cities

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sergeyrodin.citiestask.data.source.CitiesRepository
import com.sergeyrodin.citiestask.data.source.local.City
import kotlinx.coroutines.launch

class CitiesListViewModel(private val repository: CitiesRepository): ViewModel() {
    private val _cities = MutableLiveData<List<City>>()
    val cities: LiveData<List<City>>
        get() = _cities

    fun start(countryId: Long) {
        viewModelScope.launch {
            _cities.value = repository.getCitiesByCountryId(countryId)
        }
    }
}