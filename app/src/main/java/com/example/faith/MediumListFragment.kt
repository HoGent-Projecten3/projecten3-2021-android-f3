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
import com.example.faith.adapters.MediumAdapter
import com.example.faith.databinding.FragmentMediumListBinding
import com.example.faith.viewmodels.MediumListViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_medium_list.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChangedBy

/**
 * @author Remi Mestdagh
 * fragment om een lijst van films/foto's weer te geven
 */
@AndroidEntryPoint
class MediumListFragment : Fragment() {

    private val viewModel: MediumListViewModel by viewModels()
    private var searchJob: Job? = null

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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.bottom_app_bar, menu)
        super.onCreateOptionsMenu(menu, inflater)
        var searchItem: MenuItem = menu.findItem(R.id.search)
        var searchView2 = SearchView(context)
        searchItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW or MenuItem.SHOW_AS_ACTION_IF_ROOM)
        searchItem.setActionView(searchView2)
    }

    /**
     * de lijst filteren op naam
     */

    /**
     * zorgt dat je naar het cinemafragment kan gaan om een foto of video te maken
     */
    private fun navigateToCinema() {
        val direction = MediumListFragmentDirections.actionMediumListFragmentToCinemaFragment()
        val navController = findNavController()
        navController.navigate(direction)
    }

    @ExperimentalPagingApi
    override fun onResume() {
        super.onResume()
        adapter.refresh()
    }

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
