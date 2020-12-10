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
import com.example.faith.databinding.FragmentTrofeekamerListBinding
import com.example.faith.viewmodels.TrofeekamerListViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_trofeekamer_list.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

/**
 * @author Arne De Schrijver
 */

@AndroidEntryPoint
class TrofeekamerListFragment : Fragment() {

    private val viewModel: TrofeekamerListViewModel by viewModels()
    private var searchJob: Job? = null
    private var adapter = TrofeeAdapter()
    private var adapter2 = TrofeeAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentTrofeekamerListBinding.inflate(inflater, container, false)
        context ?: return binding.root
        binding.trofeeList.adapter = adapter
        binding.gedeeldeTrofeeLijst.adapter = adapter2

        getTalent()
        setHasOptionsMenu(true)

        binding.btAddTalent.setOnClickListener {
            navigateToTalent()
        }

        binding.tabTrofee.addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                println(tab.position)
                if (tab.position == 0) {
                    binding.gedeeldeTrofeeLijst.visibility = View.GONE
                    binding.trofeeList.visibility = View.VISIBLE
                    getTalent()
                } else if (tab.position == 1) {
                    binding.gedeeldeTrofeeLijst.visibility = View.VISIBLE
                    binding.trofeeList.visibility = View.GONE
                   getGedeeldeTalenten()

                } else {

                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })

        return binding.root
    }



    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.bottom_app_bar, menu)
        super.onCreateOptionsMenu(menu, inflater)
        var searchItem: MenuItem = menu.findItem(R.id.search)
        searchItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW or MenuItem.SHOW_AS_ACTION_IF_ROOM)
    }

    private fun navigateToTalent() {
        val direction =
            TrofeekamerListFragmentDirections.actionTrofeekamerListFragmentToTrofeekamerFragment()
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

    private fun getGedeeldeTalenten() {

        searchJob?.cancel()
        searchJob = lifecycleScope.launch {
            viewModel.getGedeeldeTalenten().collectLatest {
                adapter2.submitData(it)
            }
        }
    }
}