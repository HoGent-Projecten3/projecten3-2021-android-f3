package com.example.faith

import android.os.Bundle

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.faith.data.Medium
import com.example.faith.databinding.FragmentMediumDetailBinding
import com.example.faith.viewmodels.MediumDetailViewModel
import com.example.faith.viewmodels.MediumDetailViewModel.Companion.provideFactory
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_medium_detail.*
import retrofit2.Call
import javax.inject.Inject
/**
 * @author Remi Mestdagh
 */
@AndroidEntryPoint
class MediumDetailFragment : Fragment() {

    private val args: MediumDetailFragmentArgs by navArgs()

    @Inject
    lateinit var mediumDetailViewModelFactory: MediumDetailViewModel.AssistedFactory

    private val mediumDetailViewModel: MediumDetailViewModel by viewModels {
        provideFactory(
            mediumDetailViewModelFactory,
            args.mediumId
        )
    }
    fun navigateBack(){
        val direction = MediumDetailFragmentDirections.actionMediumDetailFragmentToMediumListFragment()
        val navController = findNavController()
        navController.navigate(direction)
    }

    fun removeMedium() {
        val call: Call<Medium>? = mediumDetailViewModel.removeMedium()
        mediumDetailViewModel.deleteMediumRoom()
        if (call != null) {
            call.enqueue(
                object : retrofit2.Callback<Medium?> {
                    override fun onFailure(call: Call<Medium?>, t: Throwable) {
                        activity?.let { Snackbar.make(it.findViewById(R.id.main_activity_coordinator),"Verwijderen mislukt",
                            Snackbar.LENGTH_LONG).show() }
                        navigateBack()
                    }

                    override fun onResponse(call: Call<Medium?>, response: retrofit2.Response<Medium?>) {
                        activity?.let { Snackbar.make(it.findViewById(R.id.main_activity_coordinator),"Verwijderd",
                            Snackbar.LENGTH_LONG).show() }
                        navigateBack()
                    }
                }
            )
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentMediumDetailBinding>(
            inflater,
            R.layout.fragment_medium_detail,
            container,
            false
        ).apply {
            viewModel = mediumDetailViewModel
            lifecycleOwner = viewLifecycleOwner
            callback = Callback<Any> { medium ->
                medium?.let {
                }
            }
        }

        setHasOptionsMenu(true)
        binding.btRemoveMedium.setOnClickListener {
            removeMedium()
        }
        binding.mediumVideo.start()

        return binding.root
    }

    fun interface Callback<T> {
        fun add(medium: Medium?)
    }
}
