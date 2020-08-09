package com.sergeyrodin.citiestask.countries

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.sergeyrodin.citiestask.CitiesTaskApplication
import com.sergeyrodin.citiestask.R
import com.sergeyrodin.citiestask.databinding.FragmentCountriesLIstBinding

class CountriesListFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val viewModel by viewModels<CountriesListViewModel> {
            CountriesListViewModelFactory(
                (requireContext().applicationContext as CitiesTaskApplication).citiesRepository
            )
        }
        val binding = FragmentCountriesLIstBinding.inflate(inflater)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        return binding.root
    }

}