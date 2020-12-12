package com.example.faith

import android.graphics.drawable.AnimationDrawable
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
import com.example.faith.adapters.DagboekAdapter
import com.example.faith.databinding.FragmentDagboekListBinding
import com.example.faith.viewmodels.DagboekListViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_dagboek_list.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

/**
 * @author Remi Mestdagh
 * fragment om een overzicht dagboekposts weer te geven
 */
@AndroidEntryPoint
class DagboekListFragment : Fragment() {
    private lateinit var bookIdleAnimation: AnimationDrawable
    private val viewModel: DagboekListViewModel by viewModels()
    private var adapter = DagboekAdapter()

    @ExperimentalPagingApi
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentDagboekListBinding.inflate(inflater, container, false)
        context ?: return binding.root
        binding.dagboekList.adapter = adapter

        setHasOptionsMenu(true)

        binding.btAddDagboek.setOnClickListener {
            navigateToDagboek()
        }

        //Activeer de logo animatie
        binding.bookIcon.apply { setBackgroundResource(R.drawable.book_idle)
        bookIdleAnimation = background as AnimationDrawable }
        bookIdleAnimation.start()

        initAdapter()
        return binding.root
    }

    /**
     * zoekbalk instellen
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
            }
        )
    }

    /**
     * lijst filteren op naam
     */
    @ExperimentalPagingApi
    private fun filter(text: String) {
        adapter = DagboekAdapter()
        dagboek_list.adapter = adapter
        lifecycleScope.launch {
            viewModel.filter(text).collectLatest {
                adapter.submitData(it)
            }
        }
    }

    /**
     * navigeren naar nieuw dagboekfragment
     */
    private fun navigateToDagboek() {
        val direction = DagboekListFragmentDirections.actionDagboekListFragment2ToDagboekFragment()
        val navController = findNavController()
        navController.navigate(direction)
    }

    /**
     * er is een refresh van de adapter nodig als je terugkomt van een ander scherm
     */
    @ExperimentalPagingApi
    override fun onResume() {
        super.onResume()
        adapter.refresh()
    }

    /**
     * adapter voor de eerste keer instellen
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
