package com.sergeyrodin.citiestask.countries

import androidx.lifecycle.*
import com.sergeyrodin.citiestask.data.source.CitiesRepository
import com.sergeyrodin.citiestask.data.source.CountriesRepository
import com.sergeyrodin.citiestask.data.source.Country
import kotlinx.coroutines.launch

class CountriesListViewModel(private val repository: CountriesRepository) : ViewModel() {
    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean>
        get() = _loading

    private val _countries = MutableLiveData<List<Country>>()
    val countries: LiveData<List<Country>>
        get() = _countries

    private var _error = MutableLiveData<String>()
    val error: LiveData<String>
        get() = _error

    fun start() {
        viewModelScope.launch {
            if(countries.value == null) {
                fetchCountriesFromDb()
                if(countries.value?.isEmpty() == true) {
                    fetchCountriesFromNet()
                    fetchCountriesFromDb()
                }
            }
        }
    }

    fun refresh() {
        viewModelScope.launch {
            repository.deleteAllCountries()
            fetchCountriesFromNet()
        }
    }

    private suspend fun fetchCountriesFromDb() {
        _countries.value = repository.getCountries()
    }

    private suspend fun fetchCountriesFromNet() {
        try{
            _loading.value = true
            repository.loadCountriesAndCitiesToDb()
        }catch(e: Exception) {
            _error.value = e.localizedMessage
        }finally {
            _loading.value = false
        }
    }
}

class CountriesListViewModelFactory(private val repository: CountriesRepository) :
    ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CountriesListViewModel(repository) as T
    }
}