package com.example.faith


import android.os.Bundle
import android.view.*
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.faith.adapters.HulpbronAdapter
import com.example.faith.data.*
import com.example.faith.databinding.FragmentHulpbronListBinding
import com.example.faith.viewmodels.HulpbronListViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collectLatest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


@AndroidEntryPoint
class HulpbronListFragment : Fragment() {

    private val viewModel: HulpbronListViewModel by viewModels()
    private var searchJob: Job? =null
    private val adapter = HulpbronAdapter()
    lateinit var binding: FragmentHulpbronListBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHulpbronListBinding.inflate(inflater, container, false)
        context ?: return binding.root
        binding.hulpbronList.adapter = adapter


        insertNewHulpbronnen()
        getHulpbronnen()
        binding.btnAddHulpbron.setOnClickListener {
            navigateToHulpbron()
        }
        binding.bottomAppBar.setNavigationOnClickListener {
            viewModel.cycleFilter()
            getHulpbronnen()
        }
        setHasOptionsMenu(true)
        return binding.root
    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(com.example.faith.R.menu.search_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
        val searchItem: MenuItem = menu.findItem(com.example.faith.R.id.search)
        val searchView = SearchView(context)
        searchItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM)
        searchItem.setActionView(searchView)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }
            override fun onQueryTextChange(newText: String): Boolean {
                viewModel.textFilter.value = newText
                getHulpbronnen()
                return false
            }
        })
    }

    private fun navigateToHulpbron() {
        val direction = HulpbronListFragmentDirections.actionHulpbronListFragmentToHulpbronFragment()
        val navController = findNavController()
        navController.navigate(direction)
    }

    fun insertNewHulpbronnen(){
        viewModel.getHulpbronnen2().enqueue(
            object : Callback<ApiHulpbronSearchResponse?> {
                override fun onFailure(call: Call<ApiHulpbronSearchResponse?>, t: Throwable) {
                    System.err.println("MESSAGE:" + t.message)
                    for (item in t.stackTrace) {
                        System.err.println(item.toString())
                    }
                }

                override fun onResponse(
                    call: Call<ApiHulpbronSearchResponse?>,
                    responseHulpbron: Response<ApiHulpbronSearchResponse?>
                ) {
                    val hulpbron = responseHulpbron.body()?.results
                    hulpbron?.forEach {
                        GlobalScope.async {
                            viewModel.saveOne(
                                Hulpbron(
                                    it.hulpbronId,
                                    it.auteurType,
                                    it.titel,
                                    it.inhoud,
                                    it.url,
                                    it.telefoonnummer,
                                    it.emailadres,
                                    it.chatUrl,
                                    it.datum
                                )
                            )
                        }
                    }
                }


            }
        )

    }


    private fun getHulpbronnen(){
        searchJob?.cancel()
        searchJob = lifecycleScope.launch {
            viewModel.getHulpbronnen().collectLatest {
                adapter.submitData(it)
            }
        }
    }

}
