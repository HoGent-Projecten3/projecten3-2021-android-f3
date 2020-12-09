package com.example.faith

import android.os.Bundle
import android.os.Message
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.example.faith.R
import com.example.faith.databinding.FragmentTalentBinding
import com.example.faith.viewmodels.TalentViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_dagboek.*
import retrofit2.Call
import retrofit2.Callback
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_talent.*

/**
 * @author Arne De Schrijver
 */

@AndroidEntryPoint
class TrofeekamerFragment: Fragment() {

    private val viewModel: TalentViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentTalentBinding>(
            inflater,
            R.layout.fragment_talent,
            container,
            false
        )

        binding.btSaveTalent.setOnClickListener {
            uploadTalent()
        }
        return binding.root
    }

    private fun validateInput(inhoud: String): Boolean {
        if (inhoud.isNullOrEmpty()) {
            showMessage("Gelieve een talent in te voeren")
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

    private fun uploadTalent() {
        if (validateInput(textInputInhoud.text.toString())) {


            var call: Call<Message> = viewModel.voegTalentToe(
                textInputInhoud.text.toString()
            )
            call.enqueue(
                object : Callback<Message?> {
                    override fun onFailure(call: Call<Message?>, t: Throwable) {
                        showMessage("Bewaren mislukt")
                        navigateToTalent()
                    }

                    override fun onResponse(
                        call: Call<Message?>,
                        response: retrofit2.Response<Message?>
                    ) {
                        showMessage("Bewaren gelukt")
                        navigateToTalent()
                    }
                }
            )
        }
    }

    private fun navigateToTalent() {

        val navController = findNavController()
        navController.popBackStack()
    }
}