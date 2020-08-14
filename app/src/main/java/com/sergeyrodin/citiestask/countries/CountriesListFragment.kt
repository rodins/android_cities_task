package com.sergeyrodin.citiestask.countries

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.sergeyrodin.citiestask.CitiesTaskApplication
import com.sergeyrodin.citiestask.R
import com.sergeyrodin.citiestask.databinding.FragmentCountriesListBinding
import kotlinx.android.synthetic.main.fragment_countries_list.view.*

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
        val binding = FragmentCountriesListBinding.inflate(inflater)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        binding.countriesList.adapter = CountriesListAdapter(CountriesClickListener {
            findNavController().navigate(
                CountriesListFragmentDirections.actionCountriesLIstFragmentToCitiesListFragment(it)
            )
        })

        return binding.root
    }

}