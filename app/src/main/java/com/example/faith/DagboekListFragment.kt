package com.example.faith

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.faith.adapters.DagboekAdapter
import com.example.faith.adapters.MediumAdapter
import com.example.faith.data.ApiDagboekSearchResponse
import com.example.faith.data.ApiMediumSearchResponse
import com.example.faith.data.Medium
import com.example.faith.databinding.FragmentDagboekListBinding
import com.example.faith.databinding.FragmentMediumListBinding
import com.example.faith.viewmodels.DagboekListViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@AndroidEntryPoint
class DagboekListFragment : Fragment() {

    private val viewModel: DagboekListViewModel by viewModels()
    private var searchJob: Job? =null
    private val adapter = DagboekAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentDagboekListBinding.inflate(inflater, container, false)
        context ?: return binding.root
        binding.dagboekList.adapter = adapter

        insertNewDagboekPosts()
        getDagboek()
        setHasOptionsMenu(true)

        return binding.root
    }

    fun insertNewDagboekPosts(){
        viewModel.getDagboekPosts2().enqueue(
            object : Callback<ApiDagboekSearchResponse?> {
                override fun onFailure(call: Call<ApiDagboekSearchResponse?>, t: Throwable) {
                    TODO("Not yet implemented")
                }

                override fun onResponse(
                    call: Call<ApiDagboekSearchResponse?>,
                    responseMedium: Response<ApiDagboekSearchResponse?>
                ) {
                    var fotoj = responseMedium.body()?.results
                    fotoj?.forEach {
                        GlobalScope.async {
                            viewModel.saveOne(
                                Medium(
                                    it.mediumId,
                                    it.naam,
                                    it.beschrijving,
                                  ""
                                )
                            )
                        }
                    }
                }
            }
        )

    }

    private fun getDagboek(){
        searchJob?.cancel()
        searchJob = lifecycleScope.launch {
            viewModel.getDagboekPosts().collectLatest {
                adapter.submitData(it)
            }
        }
    }

}