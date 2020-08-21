package com.sergeyrodin.citiestask.info.view

import androidx.lifecycle.Transformations.map
import androidx.lifecycle.Transformations.switchMap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class CityInfoViewModel(private val presenter: CityInfoPresenter): ViewModel() {
    val loading = presenter.loading
    val cityInfo = presenter.cityInfo
    val error = presenter.error

    val dataVisible = switchMap(loading){ loadingVisible ->
        map(error){ errorText ->
            errorText.isEmpty() && !loadingVisible
        }
    }

    fun start(country: String, city: String) {
        viewModelScope.launch {
            presenter.fetchCityInfo(country, city)
        }
    }
}

class CityInfoViewModelFactory(private val presenter: CityInfoPresenter): ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CityInfoViewModel(presenter) as T
    }
}