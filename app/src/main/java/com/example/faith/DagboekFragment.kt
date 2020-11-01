package com.example.faith

import android.os.Bundle
import android.os.Message
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.faith.databinding.FragmentDagboekBinding
import com.example.faith.viewmodels.DagboekViewModel
import dagger.hilt.android.AndroidEntryPoint
import com.example.faith.R.layout.fragment_dagboek
import kotlinx.android.synthetic.main.fragment_cinema.*
import kotlinx.android.synthetic.main.fragment_dagboek.*
import retrofit2.Call
import retrofit2.Callback

@AndroidEntryPoint
class DagboekFragment : Fragment() {
    private val viewModel: DagboekViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentDagboekBinding>(
            inflater,
            fragment_dagboek,
            container,
            false
        )

       return binding.root
    }


    private fun uploadText() {
        println(txfBericht.text.toString())
        var call: Call<Message> = viewModel.uploadDagboekPost(textInputTitel.text.toString(),textInputDescription.text.toString())
        call.enqueue(
            object : Callback<Message?> {
                override fun onFailure(call: Call<Message?>, t: Throwable) {
                    println(call.toString())
                }

                override fun onResponse(call: Call<Message?>, response: retrofit2.Response<Message?>) {
                    println(call.toString())
                }
            }
        )
    }

}