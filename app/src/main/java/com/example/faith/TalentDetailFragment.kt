package com.example.faith

import android.os.Bundle
import android.os.Message
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.faith.data.Talent
import com.example.faith.databinding.FragmentTalentDetailBinding
import com.example.faith.viewmodels.TalentDetailViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import retrofit2.Call
import javax.inject.Inject
import androidx.navigation.fragment.findNavController
import com.example.faith.viewmodels.TalentDetailViewModel.Companion.provideFactory

/**
 * @author Arne De Schrijver
 */

@AndroidEntryPoint
class TalentDetailFragment: Fragment() {

    private val args: TalentDetailFragmentArgs by navArgs()

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
        val binding = DataBindingUtil.inflate<FragmentTalentDetailBinding>(
            inflater,
            R.layout.fragment_talent_detail,
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

        binding.btRemoveTalent.setOnClickListener {
            removeTalent()
        }
        return binding.root
    }
    fun removeTalent(){
        val call: Call<Message> = talentDetailViewModel.removeTalentApi()
        talentDetailViewModel.deleteTalentRoom()
        call.enqueue(
            object : retrofit2.Callback<Message?> {
                override fun onResponse(call: Call<Message?>, response: retrofit2.Response<Message?>) {
                    activity?.let { Snackbar.make(it.findViewById(R.id.main_activity_coordinator),"Verwijderd",Snackbar.LENGTH_LONG).show() }
                    navigateToTalent()

                }
                override fun onFailure(call: Call<Message?>, t: Throwable) {
                    activity?.let { Snackbar.make(it.findViewById(R.id.main_activity_coordinator),"Verwijderen mislukt",Snackbar.LENGTH_LONG).show() }

                }

            }
        )
    }

    private fun navigateToTalent() {
        val direction = TalentDetailFragmentDirections.actionTalentDetailFragmentToTrofeekamerListFragment()
        val navController = findNavController()
        navController.navigate(direction)

    }

    fun interface Callback {
        fun add(talent: Talent?)
    }

}