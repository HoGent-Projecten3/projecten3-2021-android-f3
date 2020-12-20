package com.example.faith

import android.os.Bundle
import android.util.Log
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
import androidx.paging.flatMap
import com.example.faith.adapters.HulpbronAdapter
import com.example.faith.databinding.FragmentHulpbronListBinding
import com.example.faith.viewmodels.HulpbronListViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_infobalie.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HulpbronListFragment : Fragment() {

    private val viewModel: HulpbronListViewModel by viewModels()
    private var adapter = HulpbronAdapter()
    lateinit var binding: FragmentHulpbronListBinding

    @ExperimentalPagingApi
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHulpbronListBinding.inflate(inflater, container, false)
        context ?: return binding.root
        binding.hulpbronList.adapter = adapter
        initAdapter()

        binding.btnAddHulpbron.setOnClickListener {
            navigateToHulpbron()
        }
        binding.bottomAppBar.setNavigationOnClickListener {
            viewModel.cycleFilter()
            filter()
        }
        setHasOptionsMenu(true)
        return binding.root
    }


    /**
     * Zoek optie instellen op de appbar
     */
    @ExperimentalPagingApi
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(com.example.faith.R.menu.search_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
        val searchItem: MenuItem = menu.findItem(com.example.faith.R.id.search)
        val searchView = SearchView(context)
        searchItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW or MenuItem.SHOW_AS_ACTION_IF_ROOM)
        searchItem.setActionView(searchView)
        searchView.setOnQueryTextListener(
            object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String): Boolean {
                    return false
                }
                override fun onQueryTextChange(newText: String): Boolean {
                    viewModel.textFilter.value = newText
                    filter();
                    return false
                }
            }
        )
    }
    

    /**
     * Navigeren naar het hulpbron fragment
     */
    private fun navigateToHulpbron() {
        val navController = findNavController()
        navController.navigate(HulpbronListFragmentDirections.actionHulpbronListFragmentToHulpbronFragment())
    }

    /**
     * Filteren op titel en type hulpbron
     */
    @ExperimentalPagingApi
    private fun filter() {
        adapter = HulpbronAdapter()
        hulpbron_list.adapter = adapter
        lifecycleScope.launch {
            viewModel.filter().collectLatest {
                adapter.submitData(it)
            }
        }
    }

    /**
     * Data refreshen
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
