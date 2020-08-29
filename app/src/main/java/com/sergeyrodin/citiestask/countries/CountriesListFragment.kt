package com.sergeyrodin.citiestask.countries

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.snackbar.Snackbar
import com.sergeyrodin.citiestask.CitiesTaskApplication
import com.sergeyrodin.citiestask.R
import com.sergeyrodin.citiestask.databinding.FragmentCountriesListBinding
import kotlinx.android.synthetic.main.fragment_countries_list.view.*

class CountriesListFragment : Fragment() {

    private val viewModel by viewModels<CountriesListViewModel> {
        CountriesListViewModelFactory(
            (requireContext().applicationContext as CitiesTaskApplication).countriesRepository
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

        viewModel.start()

        swipeRefresh = binding.swiperefresh
        swipeRefresh.setOnRefreshListener {
            viewModel.refresh()
        }

        viewModel.loading.observe(viewLifecycleOwner, Observer{ isLoading ->
            swipeRefresh.isRefreshing = isLoading
        })

        viewModel.error.observe(viewLifecycleOwner, Observer{ error ->
            if(error.isNotEmpty()) {
                Snackbar.make(requireView(), error, Snackbar.LENGTH_LONG).show()
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