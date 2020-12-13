package com.example.faith

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.faith.data.Medium
import com.example.faith.databinding.FragmentMediumDetailBinding
import com.example.faith.viewmodels.MediumDetailViewModel
import com.example.faith.viewmodels.MediumDetailViewModel.Companion.provideFactory
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_medium_detail.*
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

        binding.mediumVideo.start()

        return binding.root
    }

    fun interface Callback<T> {
        fun add(medium: Medium?)
    }
}
