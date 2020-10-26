package com.example.faith

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.example.faith.adapters.MediumAdapter
import com.example.faith.databinding.FragmentMediumListBinding
import com.example.faith.viewmodels.MediumListViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MediumListFragment : Fragment() {

    private val viewModel: MediumListViewModel by viewModels()
    private var searchJob: Job? = null
    private val adapter = MediumAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentMediumListBinding.inflate(inflater, container, false)
        context ?: return binding.root
        binding.mediumList.adapter = adapter

        getMedia()
        setHasOptionsMenu(true)
        return binding.root
    }


    private fun getMedia(){
        searchJob?.cancel()
        searchJob = lifecycleScope.launch{
            viewModel.searchPictures().collectLatest {
                adapter.submitData(it)
            }
        }

    }


}