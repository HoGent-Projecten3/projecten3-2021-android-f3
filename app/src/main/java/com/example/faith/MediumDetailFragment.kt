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
import com.example.faith.data.Medium
import com.example.faith.databinding.FragmentMediumDetailBinding
import com.example.faith.viewmodels.MediumDetailViewModel
import com.example.faith.viewmodels.MediumDetailViewModel.Companion.provideFactory
import dagger.hilt.android.AndroidEntryPoint
import retrofit2.Call
import retrofit2.Callback
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

    fun removeMedium() {
        val call: Call<Message> = mediumDetailViewModel.removeMedium()
        mediumDetailViewModel.deleteMediumRoom()
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

        return binding.root
    }

    fun interface Callback<T> {
        fun add(medium: Medium?)
    }
}
