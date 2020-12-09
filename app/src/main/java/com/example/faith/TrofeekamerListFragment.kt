package com.example.faith

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.faith.adapters.TrofeeAdapter
import com.example.faith.data.ApiTalentSearchResponse
import com.example.faith.databinding.FragmentTrofeeListBinding
import com.example.faith.viewmodels.TrofeekamerListViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
/**
 * @author Arne De Schrijver
 */

@AndroidEntryPoint
class TrofeekamerListFragment: Fragment() {

    private val viewModel: TrofeekamerListViewModel by viewModels()
    private var searchJob: Job? = null
    private var adapter = TrofeeAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentTrofeeListBinding.inflate(inflater, container, false)
        context ?: return binding.root
        binding.trofeeList.adapter = adapter

        getTalent()
        setHasOptionsMenu(true)

        binding.btAddTalent.setOnClickListener {
            navigateToTalent()
        }
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.bottom_app_bar, menu)
        super.onCreateOptionsMenu(menu, inflater)
        var searchItem: MenuItem = menu.findItem(R.id.searchBib)
        searchItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW or MenuItem.SHOW_AS_ACTION_IF_ROOM)
    }

    private fun navigateToTalent() {
        val direction = TrofeekamerListFragmentDirections.actionTrofeekamerListFragmentToTrofeekamerFragment()
        val navController = findNavController()
        navController.navigate(direction)
    }

    private fun getTalent() {
        searchJob?.cancel()
        searchJob = lifecycleScope.launch {
            viewModel.getTalenten().collectLatest {
                adapter.submitData(it)
            }
        }
    }

}