package com.sergeyrodin.citiestask.countries

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
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

    private lateinit var swipeRefresh: SwipeRefreshLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = FragmentCountriesListBinding.inflate(inflater)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        binding.countriesList.adapter = CountriesListAdapter(CountriesClickListener { id: Long, name: String ->
            findNavController().navigate(
                CountriesListFragmentDirections.actionCountriesLIstFragmentToCitiesListFragment(id, name)
            )
        })

        swipeRefresh = binding.swiperefresh
        swipeRefresh.setOnRefreshListener {
            viewModel.refresh()
        }

        viewModel.countries.observe(viewLifecycleOwner, Observer{
            if(it.isEmpty()) {
                viewModel.loadCountries()
            }
        })

        viewModel.loading.observe(viewLifecycleOwner, Observer{ isLoading ->
            if(!isLoading) {
                swipeRefresh.isRefreshing = false
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
            swipeRefresh.isRefreshing = true
            viewModel.refresh()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}