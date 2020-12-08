package com.example.faith

import android.os.Bundle
import android.os.Message
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.faith.R.layout.fragment_dagboek
import com.example.faith.data.Medium
import com.example.faith.databinding.FragmentDagboekBinding
import com.example.faith.viewmodels.DagboekViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_cinema.*
import kotlinx.android.synthetic.main.fragment_dagboek.*
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback

/**
 * @author Remi Mestdagh
 */
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

        binding.btSaveDagboek.setOnClickListener {
            uploadText()
        }
        return binding.root
    }

    private fun validateInput(titel: String, beschrijving: String): Boolean {
        if (titel.isNullOrEmpty()) {
            showMessage("Gelieve een titel in te voeren")
            return false
        }
        if (beschrijving.isNullOrEmpty()) {
            showMessage("Gelieve een beschrijving in te voeren")
            return false
        }
        return true
    }

    private fun showMessage(message: String) {
        activity?.let {
            Snackbar.make(
                it.findViewById(R.id.main_activity_coordinator), message,
                Snackbar.LENGTH_LONG
            ).show()
        }
    }

    private fun uploadText() {
        if (validateInput(textInputTitel.text.toString(), textInputDescription.text.toString())) {


        var call: Call<Message> = viewModel.uploadDagboekPost(
            textInputTitel.text.toString(),
            textInputDescription.text.toString()
        )
        call.enqueue(
            object : Callback<Message?> {
                override fun onFailure(call: Call<Message?>, t: Throwable) {
                    showMessage("Bewaren mislukt")
                    navigateToDagboek()
                }

                override fun onResponse(
                    call: Call<Message?>,
                    response: retrofit2.Response<Message?>
                ) {
                    showMessage("Bewaren gelukt")
                    navigateToDagboek()
                }
            }
        )
    }
    }

    private fun navigateToDagboek() {

        val navController = findNavController()
        navController.popBackStack()
    }
}
