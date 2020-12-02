package com.example.faith

import android.os.Bundle
import android.os.Message
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.NavigationUI
import com.example.faith.data.ApiMediumResponse
import com.example.faith.data.Medium
import com.example.faith.databinding.FragmentDagboekDetailBinding
import com.example.faith.viewmodels.DagboekDetailViewModel
import com.example.faith.viewmodels.DagboekDetailViewModel.Companion.provideFactory
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_dagboek_detail.*
import kotlinx.android.synthetic.main.fragment_dagboek_list.*
import retrofit2.Call
import javax.inject.Inject
/**
 * @author Remi Mestdagh
 * detailoverzicht van een dagboekpost
 */
@AndroidEntryPoint
class DagboekDetailFragment : Fragment() {

    private val args: DagboekDetailFragmentArgs by navArgs()

    @Inject
    lateinit var dagboekDetailViewModelFactory: DagboekDetailViewModel.AssistedFactory

    private val dagboekDetailViewModel: DagboekDetailViewModel by viewModels {
        provideFactory(
            dagboekDetailViewModelFactory,
            args.mediumId
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentDagboekDetailBinding>(
            inflater,
            R.layout.fragment_dagboek_detail,
            container,
            false
        ).apply {
            viewModel = dagboekDetailViewModel
            lifecycleOwner = viewLifecycleOwner
            callback = Callback { medium ->
                medium?.let {
                }
            }
        }

        setHasOptionsMenu(true)

        binding.btRemoveDagboek.setOnClickListener {
            removeMedium()
        }
        return binding.root
    }

    /**
     * verwijdert het medium
     */
    fun removeMedium() {
        val call: Call<ApiMediumResponse> = dagboekDetailViewModel.removeMediumApi()
        dagboekDetailViewModel.deleteMediumRoom()
        call.enqueue(
            object : retrofit2.Callback<ApiMediumResponse?> {
                override fun onResponse(call: Call<ApiMediumResponse?>, response: retrofit2.Response<ApiMediumResponse?>) {
                    activity?.let { Snackbar.make(it.findViewById(R.id.main_activity_coordinator),"Verwijderd",Snackbar.LENGTH_LONG).show() }
                    navigateToDagboek()

                }
                override fun onFailure(call: Call<ApiMediumResponse?>, t: Throwable) {
                    activity?.let { Snackbar.make(it.findViewById(R.id.main_activity_coordinator),"Verwijderen mislukt",Snackbar.LENGTH_LONG).show() }

                }


            }
        )
    }

    /**
     * navigeert terug naar het algemeen overzicht van dagboekposts
     */
    private fun navigateToDagboek() {
        val direction = DagboekDetailFragmentDirections.actionDagboekDetailFragmentToDagboekListFragment2()
        val navController = findNavController()
        navController.navigate(direction)

    }

    fun interface Callback {
        fun add(medium: Medium?)
    }
}
