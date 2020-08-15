package com.sergeyrodin.citiestask.countries

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sergeyrodin.citiestask.data.source.local.Country
import com.sergeyrodin.citiestask.databinding.CountryListItemBinding

class CountriesListAdapter(private val clickListener: CountriesClickListener): ListAdapter<Country, CountriesListAdapter.ViewHolder>(CountriesDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), clickListener)
    }

    class ViewHolder private constructor (private val binding: CountryListItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(country: Country, listener: CountriesClickListener) {
            binding.country = country
            binding.clickListener = listener
            binding.executePendingBindings()
        }

        companion object{
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = CountryListItemBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}

class CountriesDiffCallback: DiffUtil.ItemCallback<Country>(){

    override fun areItemsTheSame(oldItem: Country, newItem: Country): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Country, newItem: Country): Boolean {
        return oldItem == newItem
    }

}

class CountriesClickListener(private val clickListener: (id: Long, name: String) -> Unit) {
    fun onClick(id: Long, name: String) = clickListener(id, name)
}