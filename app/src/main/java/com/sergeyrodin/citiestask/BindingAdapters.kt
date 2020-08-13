package com.sergeyrodin.citiestask

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sergeyrodin.citiestask.cities.CitiesListAdapter
import com.sergeyrodin.citiestask.countries.CountriesListAdapter
import com.sergeyrodin.citiestask.data.source.local.City
import com.sergeyrodin.citiestask.data.source.local.Country

@BindingAdapter("countriesListData")
fun bindCountryRecyclerView(view: RecyclerView, list: List<Country>?) {
    val adapter = view.adapter as CountriesListAdapter
    adapter.submitList(list)
}

@BindingAdapter("citiesListData")
fun bindCityRecyclerView(view: RecyclerView, list: List<City>?) {
    val adapter = view.adapter as CitiesListAdapter
    adapter.submitList(list)
}