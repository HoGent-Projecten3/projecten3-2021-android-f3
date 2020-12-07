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
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState
import com.example.faith.adapters.DagboekAdapter
import com.example.faith.adapters.MediumAdapter
import com.example.faith.databinding.FragmentMediumListBinding
import com.example.faith.viewmodels.MediumListViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_dagboek_list.*
import kotlinx.android.synthetic.main.fragment_medium_list.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.launch

/**
 * @author Remi Mestdagh
 * fragment om een lijst van films/foto's weer te geven
 */
@AndroidEntryPoint
class MediumListFragment : Fragment() {
    private val viewModel: MediumListViewModel by viewModels()
    private var adapter = MediumAdapter()

    @ExperimentalPagingApi
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentMediumListBinding.inflate(inflater, container, false)
        context ?: return binding.root
        binding.mediumList.adapter = adapter

        setHasOptionsMenu(true)
        binding.btGoToCinema.setOnClickListener {
            navigateToCinema()
        }
        initAdapter()


        return binding.root
    }

    /**
     * zoekbalk inflaten en listener instellen
     */
    @ExperimentalPagingApi
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
            })
    }

    /**
     * de lijst filteren op naam
     */
    @ExperimentalPagingApi
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

    /**
     * wanneer je terugkomt van een ander fragment moet de adapter upgedatet worden
     */
    @ExperimentalPagingApi
    override fun onResume() {
        super.onResume()
        adapter.refresh()
    }

    /**
     * adapter instellen
     */
    @ExperimentalPagingApi
    private fun initAdapter() {

        lifecycleScope.launchWhenCreated {
            @OptIn(ExperimentalCoroutinesApi::class)
            viewModel.posts.collectLatest {
                adapter.submitData(it)
            }
        }
    }
}
