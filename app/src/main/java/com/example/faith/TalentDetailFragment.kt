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
import dagger.hilt.android.AndroidEntryPoint
import retrofit2.Call
import javax.inject.Inject
/**
 * @author Arne De Schrijver
 */

@AndroidEntryPoint
class TalentDetailFragment: Fragment() {

    private val args: TalentDetailFragmentArgs by navArgs()

    @Inject
    lateinit var talentDetailViewModelFactory: TalentDetailViewModel.AssistedFactory

    private val talentDetailViewModel: TalentDetailViewModel by viewModels {
        TalentDetailViewModel.provideFactory(
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
        talentDetailViewModel.deleteTalentRoom(args.talentId)
        call.enqueue(
            object : retrofit2.Callback<Message?> {
                override fun onFailure(call: Call<Message?>, t: Throwable) {
                    println(call.toString())
                }

                override fun onResponse(call: Call<Message?>, response: retrofit2.Response<Message?>) {
                    println(call.toString())
                }
            }
        )
    }

    fun interface Callback {
        fun add(talent: Talent?)
    }

}