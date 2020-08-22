package com.sergeyrodin.citiestask

import android.view.View
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
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

@BindingAdapter("viewVisible")
fun bindViewBooleanVisible(view: View, visible: Boolean) {
    view.visibility = if(visible) View.VISIBLE else View.INVISIBLE
}

@BindingAdapter("imageUrl")
fun bindImage(imgView: ImageView, imgUrl: String?) {
    imgUrl?.let{
        if(imgUrl.isNotEmpty()) {
            val imgUri = imgUrl.toUri().buildUpon().scheme("https").build()
            Glide.with(imgView.context)
                .load(imgUri)
                .apply(
                    RequestOptions()
                        .placeholder(R.drawable.loading_animation)
                        .error(R.drawable.ic_broken_image))
                .into(imgView)
        }
    }
}