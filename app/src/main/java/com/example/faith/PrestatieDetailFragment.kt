package com.example.faith

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.faith.data.Talent
import com.example.faith.databinding.FragmentPrestatieDetailBinding
import com.example.faith.viewmodels.TalentDetailViewModel
import com.example.faith.viewmodels.TalentDetailViewModel.Companion.provideFactory
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * @author Arne De Schrijver
 */

@AndroidEntryPoint
class PrestatieDetailFragment : Fragment() {

    private val args: PrestatieDetailFragmentArgs by navArgs()

    @Inject
    lateinit var talentDetailViewModelFactory: TalentDetailViewModel.AssistedFactory

    private val talentDetailViewModel: TalentDetailViewModel by viewModels {
        provideFactory(
            talentDetailViewModelFactory,
            args.talentId
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentPrestatieDetailBinding>(
            inflater,
            R.layout.fragment_prestatie_detail,
            container,
            false
        ).apply {
            viewModel = talentDetailViewModel
            lifecycleOwner = viewLifecycleOwner
            callback = Callback { talent ->
                talent?.let {
                }
            }
        }

        setHasOptionsMenu(true)

        return binding.root
    }

    fun interface Callback {
        fun add(talent: Talent?)
    }

}