package com.sergeyrodin.citiestask.info.view

import androidx.lifecycle.Transformations.map
import androidx.lifecycle.Transformations.switchMap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class CityInfoViewModel(private val presenter: ICityInfoPresenter): ViewModel() {
    val loading = presenter.loading
    val cityInfo = presenter.cityInfo
    val error = presenter.error

    val dataVisible = switchMap(loading){ loadingVisible ->
        switchMap(cityInfo) { cityInfoData ->
            map(error){ errorText ->
                errorText.isEmpty() && !loadingVisible && cityInfoData.title.isNotEmpty()
            }
        }
    }

    val emptyCityInfoText = map(cityInfo){
        it.title.isEmpty()
    }

    fun start(country: String, city: String) {
        viewModelScope.launch {
            presenter.fetchCityInfo(country, city)
        }
    }
}

class CityInfoViewModelFactory(private val presenter: ICityInfoPresenter): ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CityInfoViewModel(presenter) as T
    }
}