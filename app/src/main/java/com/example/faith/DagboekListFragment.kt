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
import com.example.faith.databinding.FragmentDagboekListBinding
import com.example.faith.databinding.FragmentMediumListBinding
import com.example.faith.viewmodels.DagboekListViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

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