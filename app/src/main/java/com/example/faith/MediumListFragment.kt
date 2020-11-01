package com.example.faith

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.faith.adapters.MediumAdapter
import com.example.faith.data.ApiMediumSearchResponse
import com.example.faith.data.Medium
import com.example.faith.databinding.FragmentMediumListBinding
import com.example.faith.viewmodels.MediumListViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_medium_list.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
/**
 * @author Remi Mestdagh
 */
@AndroidEntryPoint
class MediumListFragment : Fragment() {

    private val viewModel: MediumListViewModel by viewModels()
    private var searchJob: Job? = null
    private val adapter = MediumAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentMediumListBinding.inflate(inflater, container, false)
        context ?: return binding.root
        binding.mediumList.adapter = adapter

        insertNewMedia()
        getMedia()
        setHasOptionsMenu(true)
        binding.btGoToCinema.setOnClickListener {
            navigateToCinema()
        }
        binding.bottomAppBarBib.setNavigationOnClickListener {
            it.setOnClickListener {
                navigateToDagboek()
            }
        }

        return binding.root
    }

    private fun navigateToDagboek() {
        val direction = MediumListFragmentDirections.actionMediumListFragmentToDagboekListFragment2()
        val navController = findNavController()
        navController.navigate(direction)
    }
    private fun navigateToCinema() {
        val direction = MediumListFragmentDirections.actionMediumListFragmentToCinemaFragment()
        val navController = findNavController()
        navController.navigate(direction)
    }
    fun insertNewMedia() {
        viewModel.getMedia2().enqueue(
            object : Callback<ApiMediumSearchResponse?> {
                override fun onFailure(call: Call<ApiMediumSearchResponse?>, t: Throwable) {
                    TODO("Not yet implemented")
                }

                override fun onResponse(
                    call: Call<ApiMediumSearchResponse?>,
                    responseMedium: Response<ApiMediumSearchResponse?>
                ) {
                    var fotoj = responseMedium.body()?.results
                    fotoj?.forEach {

                        viewModel.saveOne(
                            Medium(
                                it.mediumId,
                                it.naam,
                                it.beschrijving,
                                it.url
                            )
                        )
                    }
                }
            }
        )
    }

    private fun getMedia() {
        searchJob?.cancel()
        searchJob = lifecycleScope.launch {
            viewModel.searchPictures().collectLatest {
                adapter.submitData(it)
            }
        }
    }
}
