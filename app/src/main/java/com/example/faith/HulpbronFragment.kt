package com.example.faith

import android.os.Bundle
import android.os.Message
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.navigation.fragment.findNavController
import com.example.faith.databinding.FragmentHulpbronBinding
import com.example.faith.viewmodels.HulpbronViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_cinema.*
import kotlinx.android.synthetic.main.fragment_hulpbron.*
import kotlinx.android.synthetic.main.fragment_hulpbron_list.view.*
import retrofit2.Call
import retrofit2.Callback
/**
 * @author Remi Mestdagh
 */
@AndroidEntryPoint
class HulpbronFragment : Fragment() {
    private val viewModel: HulpbronViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentHulpbronBinding>(
            inflater,
            R.layout.fragment_hulpbron,
            container,
            false
        )

        binding.btnSaveHulpbron.setOnClickListener {
            uploadText()
        }
        return binding.root
    }

    private fun uploadText() {
        var call: Call<Message> = viewModel.maakHulpbron(textInputTitel.text.toString(), textInputBeschrijving.text.toString(),textInputUrl.text.toString(),textInputTelefoonnummer.text.toString(),textInputEmailadres.text.toString(),textInputChatUrl.text.toString())
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
