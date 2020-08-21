package com.sergeyrodin.citiestask.info.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.sergeyrodin.citiestask.CitiesTaskApplication
import com.sergeyrodin.citiestask.databinding.FragmentCityInfoBinding

class CityInfoFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentCityInfoBinding.inflate(inflater, container, false)
        val args by navArgs<CityInfoFragmentArgs>()
        val viewModel by viewModels<CityInfoViewModel>{
            CityInfoViewModelFactory(
                (requireContext().applicationContext as CitiesTaskApplication).cityInfoDataSource
            )
        }

        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        viewModel.start(args.country, args.city)

        return binding.root
    }

}