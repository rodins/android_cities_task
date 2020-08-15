package com.sergeyrodin.citiestask.countries

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.sergeyrodin.citiestask.CitiesTaskApplication
import com.sergeyrodin.citiestask.R
import com.sergeyrodin.citiestask.databinding.FragmentCountriesListBinding
import kotlinx.android.synthetic.main.fragment_countries_list.view.*

class CountriesListFragment : Fragment() {

    private val viewModel by viewModels<CountriesListViewModel> {
        CountriesListViewModelFactory(
            (requireContext().applicationContext as CitiesTaskApplication).citiesRepository
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = FragmentCountriesListBinding.inflate(inflater)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        binding.countriesList.adapter = CountriesListAdapter(CountriesClickListener {
            findNavController().navigate(
                CountriesListFragmentDirections.actionCountriesLIstFragmentToCitiesListFragment(it)
            )
        })

        viewModel.countries.observe(viewLifecycleOwner, Observer{
            if(it.isEmpty()) {
                viewModel.loadCountries()
            }
        })

        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.countries_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.action_refresh) {
            viewModel.refresh()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}