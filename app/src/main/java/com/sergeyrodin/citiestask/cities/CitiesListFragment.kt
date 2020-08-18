package com.sergeyrodin.citiestask.cities

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.sergeyrodin.citiestask.CitiesTaskApplication
import com.sergeyrodin.citiestask.R
import com.sergeyrodin.citiestask.databinding.FragmentCitiesListBinding

class CitiesListFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentCitiesListBinding.inflate(inflater)
        val viewModel by viewModels<CitiesListViewModel> {
            CitiesListViewModelFactory(
                (requireContext().applicationContext as CitiesTaskApplication).citiesRepository
            )
        }
        val args by navArgs<CitiesListFragmentArgs>()
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        binding.citiesList.adapter = CitiesListAdapter(CitiesListClickListener {
            findNavController().navigate(
                CitiesListFragmentDirections.actionCitiesListFragmentToCityInfoFragment(it)
            )
        })

        viewModel.start(args.countryId)

        return binding.root
    }
}