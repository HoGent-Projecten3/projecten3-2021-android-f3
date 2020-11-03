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
import com.example.faith.databinding.FragmentDagboekDetailBinding
import com.example.faith.viewmodels.DagboekDetailViewModel
import com.example.faith.viewmodels.DagboekDetailViewModel.Companion.provideFactory
import dagger.hilt.android.AndroidEntryPoint
import retrofit2.Call
import javax.inject.Inject
/**
 * @author Remi Mestdagh
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
    fun removeMedium(){
        val call: Call<Message> = dagboekDetailViewModel.removeMediumApi()
        dagboekDetailViewModel.deleteMediumRoom(args.mediumId)
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
        fun add(medium: Medium?)
    }
}
