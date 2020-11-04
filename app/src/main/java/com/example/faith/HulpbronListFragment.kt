package com.example.faith

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.faith.adapters.HulpbronAdapter
import com.example.faith.adapters.MediumAdapter
import com.example.faith.data.*
import com.example.faith.databinding.FragmentHoofdschermBinding
import com.example.faith.databinding.FragmentHulpbronDetailBinding
import com.example.faith.databinding.FragmentHulpbronListBinding
import com.example.faith.databinding.FragmentMediumListBinding
import com.example.faith.viewmodels.HulpbronListViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_hulpbron_list.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
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
        getHulpbron()

        binding.svHulpbron.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextChange(newText: String): Boolean {
                searchJob?.cancel()
                searchJob = lifecycleScope.launch {
                    viewModel.getHulpbronnen().collectLatest {
                        adapter.filter(newText);
                    }
                }
                return false
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                searchJob?.cancel()
                searchJob = lifecycleScope.launch {
                    viewModel.getHulpbronnen().collectLatest {
                        adapter.filter(query);
                    }
                }
                return false
            }

        })
        setHasOptionsMenu(true)
        return binding.root



    }

    fun insertNewHulpbronnen(){
        viewModel.getHulpbronnen2().enqueue(
            object : Callback<ApiHulpbronSearchResponse?> {
                override fun onFailure(call: Call<ApiHulpbronSearchResponse?>, t: Throwable) {
                    System.err.println("MESSAGE:" + t.message)
                   for (item in t.stackTrace)
                   {
                       System.err.println(item.toString())
                   }
                }
                override fun onResponse(
                    call: Call<ApiHulpbronSearchResponse?>,
                    responseHulpbron: Response<ApiHulpbronSearchResponse?>
                ) {
                    var hulpbron = responseHulpbron.body()?.results
                    hulpbron?.forEach {
                        GlobalScope.async {
                            viewModel.saveOne(
                                Hulpbron(
                                    it.hulpbronId,
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

    private fun getHulpbron(){
        searchJob?.cancel()
        searchJob = lifecycleScope.launch {
            viewModel.getHulpbronnen().collectLatest {
                adapter.submitData(it)
            }
        }
    }

}
