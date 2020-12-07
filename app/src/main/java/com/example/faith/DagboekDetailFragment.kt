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


        return binding.root
    }


    fun interface Callback {
        fun add(medium: Medium?)
    }
}
