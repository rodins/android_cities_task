package com.sergeyrodin.citiestask.countries

import androidx.lifecycle.*
import com.sergeyrodin.citiestask.data.source.CitiesRepository
import com.sergeyrodin.citiestask.data.source.remote.CitiesApi
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CountriesListViewModel(repository: CitiesRepository): ViewModel() {

    private val _response = MutableLiveData<String>()
    val response: LiveData<String>
        get() = _response

    init {
        viewModelScope.launch{
            _response.value = "Loading..."
            _response.value = repository.getJsonText()
        }
    }

}

class CountriesListViewModelFactory(private val repository: CitiesRepository): ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CountriesListViewModel(repository) as T
    }
}