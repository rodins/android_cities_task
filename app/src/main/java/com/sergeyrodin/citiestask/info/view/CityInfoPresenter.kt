package com.sergeyrodin.citiestask.info.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sergeyrodin.citiestask.info.CityInfo
import com.sergeyrodin.citiestask.info.interactor.ICityInfoInteractor
import java.lang.Exception

class CityInfoPresenter(private val cityInfoInteractor: ICityInfoInteractor): ICityInfoPresenter {
    private val _loading = MutableLiveData<Boolean>()
    override val loading: LiveData<Boolean>
        get() = _loading

    private val _cityInfo = MutableLiveData<CityInfo>()
    override val cityInfo: LiveData<CityInfo>
        get() = _cityInfo

    private val _error = MutableLiveData<String>()
    override val error: LiveData<String>
        get() = _error

    override suspend fun fetchCityInfo(country: String, city: String) {
        if(_cityInfo.value == null){
            try {
                _loading.value = true
                _error.value = ""
                _cityInfo.value = cityInfoInteractor.fetchCityInfo(country, city)
            }catch(e: Exception) {
                _error.value = e.localizedMessage
            }finally {
                _loading.value = false
            }
        }
    }
}