package com.example.faith

import android.os.Bundle
import android.os.Message
import android.util.Log
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.ExperimentalPagingApi
import com.example.faith.adapters.DagboekAdapter
import com.example.faith.adapters.HulpbronAdapter
import com.example.faith.databinding.FragmentHulpbronBinding
import com.example.faith.viewmodels.HulpbronViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_cinema.*
import kotlinx.android.synthetic.main.fragment_dagboek_list.*
import kotlinx.android.synthetic.main.fragment_hulpbron.*
import kotlinx.android.synthetic.main.fragment_hulpbron_list.view.*
import kotlinx.android.synthetic.main.fragment_infobalie.*
import kotlinx.android.synthetic.main.fragment_penthouse.*
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback

@AndroidEntryPoint
class HulpbronFragment : Fragment() {
    private val viewModel: HulpbronViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = DataBindingUtil.inflate<FragmentHulpbronBinding>(
            inflater,
            R.layout.fragment_hulpbron,
            container,
            false
        )

        binding.btnSaveHulpbron.setOnClickListener {
            uploadHulpbron()
        }
        return binding.root
    }

    /**
     * Terug naar de lijst navigeren
     */
    private fun navigateToHulpbronList() {
        val navController = findNavController()
        navController.popBackStack()
    }

    /**
     * Een hulpbron aanmaken
     */
    private fun uploadHulpbron() {
        val titel = textInputTitel.text.toString()
        val beschrijving = textInputBeschrijving.text.toString()
        val emailadres = textInputEmailadres.text.toString()
        val url = textInputUrl.text.toString()
        val telefoonnummer = textInputTelefoonnummer.text.toString()
        val chatUrl = textInputChatUrl.text.toString()

        if (validateInput(titel, beschrijving, url, telefoonnummer, emailadres, chatUrl)) {
            val call: Call<Message> = viewModel.maakHulpbron(titel, beschrijving, url, telefoonnummer, emailadres, chatUrl)
            call.enqueue(
                object : Callback<Message?> {
                    override fun onFailure(call: Call<Message?>, t: Throwable) {
                        showMessage("Opslaan mislukt")
                        navigateToHulpbronList()
                    }

                    override fun onResponse(call: Call<Message?>, response: retrofit2.Response<Message?>) {
                        showMessage("Bewaren gelukt")
                        navigateToHulpbronList()
                    }
                }
            )
        }
    }

    /**
     * Valideren of de input van de hulpbron geldig is
     */
    private fun validateInput(titel: String, beschrijving: String, url: String, telefoonnummer: String, emailadres: String, chatUrl: String): Boolean {
        if (titel.isNullOrEmpty()) {
            showMessage(resources.getString(R.string.legeTitel))
            return false
        }
        if (beschrijving.isNullOrEmpty()) {
            showMessage(resources.getString(R.string.legeBeschrijving))
            return false
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(emailadres).matches() and emailadres.isNotEmpty()) {
            showMessage(resources.getString(R.string.ongeldigEmailadres))
            return false
        }
        if (!Patterns.WEB_URL.matcher(url).matches() and url.isNotEmpty()) {
            showMessage(resources.getString(R.string.ongeldigeUrl))
            return false
        }
        if (!Patterns.WEB_URL.matcher(chatUrl).matches() and chatUrl.isNotEmpty()) {
            showMessage(resources.getString(R.string.ongeldigeChatUrl))
            return false
        }
        if (!Patterns.PHONE.matcher(telefoonnummer).matches() and telefoonnummer.isNotEmpty()) {
            showMessage(resources.getString(R.string.ongeldigTelefoonnummer))
            return false
        }
        return true
    }

    /**
     * Boodschap geven aan gebruiker
     */
    private fun showMessage(message: String) {
        activity?.let {
            Snackbar.make(
                    it.findViewById(R.id.main_activity_coordinator),
                    message,
                    Snackbar.LENGTH_LONG
            ).show()
        }
    }
}
