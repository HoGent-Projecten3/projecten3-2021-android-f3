package com.example.faith

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.faith.adapters.MediumAdapter
import com.example.faith.data.ApiMediumSearchResponse
import com.example.faith.data.Medium
import com.example.faith.databinding.FragmentMediumListBinding
import com.example.faith.viewmodels.MediumListViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_medium_list.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * @author Remi Mestdagh
 * fragment om een lijst van films/foto's weer te geven
 */
@AndroidEntryPoint
class MediumListFragment : Fragment() {

    private val viewModel: MediumListViewModel by viewModels()
    private var searchJob: Job? = null
    private var adapter = MediumAdapter()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentMediumListBinding.inflate(inflater, container, false)
        context ?: return binding.root
        binding.mediumList.adapter = adapter
        getMedia()
        setHasOptionsMenu(true)
        binding.btGoToCinema.setOnClickListener {
            navigateToCinema()
        }

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.bottom_app_bar, menu)
        super.onCreateOptionsMenu(menu, inflater)
        var searchItem: MenuItem = menu.findItem(R.id.search)
        var searchView2 = SearchView(context)
        searchItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW or MenuItem.SHOW_AS_ACTION_IF_ROOM)
        searchItem.setActionView(searchView2)
        searchView2.setOnQueryTextListener(
            object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String): Boolean {
                    filter(query)
                    return false
                }

                override fun onQueryTextChange(newText: String): Boolean {
                    filter(newText)
                    return false
                }
            }
        )
    }

    /**
     * de lijst filteren op naam
     */
    private fun filter(text: String) {
        adapter = MediumAdapter()
        medium_list.adapter = adapter
        lifecycleScope.launch {
            viewModel.filter(text).collectLatest {
                adapter.submitData(it)
            }
        }
    }

    /**
     * zorgt dat je naar het cinemafragment kan gaan om een foto of video te maken
     */
    private fun navigateToCinema() {
        val direction = MediumListFragmentDirections.actionMediumListFragmentToCinemaFragment()
        val navController = findNavController()
        navController.navigate(direction)
    }

    private fun getMedia() {
        searchJob?.cancel()
        searchJob = lifecycleScope.launch {
            viewModel.searchPictures().collectLatest {
                adapter.submitData(it)
            }
        }
    }
}
