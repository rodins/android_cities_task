package com.sergeyrodin.citiestask.countries

import androidx.lifecycle.*
import com.sergeyrodin.citiestask.data.source.CitiesRepository
import com.sergeyrodin.citiestask.data.source.local.Country
import com.sergeyrodin.citiestask.data.source.remote.CitiesApi
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CountriesListViewModel(repository: CitiesRepository): ViewModel() {

    val loading = repository.loading
    val error = repository.error

    private val _countries = MutableLiveData<List<Country>>()
    val countries: LiveData<List<Country>>
        get() = _countries

    init{
        viewModelScope.launch {
            _countries.value = repository.getCountries()
        }
    }

}

class CountriesListViewModelFactory(private val repository: CitiesRepository): ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CountriesListViewModel(repository) as T
    }
}