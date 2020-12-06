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
import androidx.navigation.fragment.findNavController
import com.example.faith.databinding.FragmentHulpbronBinding
import com.example.faith.viewmodels.HulpbronViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_cinema.*
import kotlinx.android.synthetic.main.fragment_hulpbron.*
import kotlinx.android.synthetic.main.fragment_hulpbron_list.view.*
import kotlinx.android.synthetic.main.fragment_penthouse.*
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

    private fun navigateToHulpbronList() {
        val direction = HulpbronFragmentDirections.actionHulpbronFragmentToHulpbronListFragment()
        val navController = findNavController()
        navController.navigate(direction)
    }

    private fun uploadHulpbron() {
        val titel = textInputTitel.text.toString()
        val beschrijving = textInputBeschrijving.text.toString()
        val emailadres = textInputEmailadres.text.toString()
        val url = textInputUrl.text.toString()
        val telefoonnummer = textInputTelefoonnummer.text.toString()
        val chatUrl = textInputChatUrl.text.toString()

        if (validateInput(titel, beschrijving, url, telefoonnummer, emailadres, chatUrl))
        {
            val call: Call<Message> = viewModel.maakHulpbron(titel, beschrijving, url, telefoonnummer, emailadres, chatUrl)
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
            navigateToHulpbronList()
        }
    }
    private fun validateInput(titel: String, beschrijving: String, url: String, telefoonnummer: String, emailadres: String, chatUrl: String) : Boolean
    {
        if (titel.isNullOrEmpty())
        {
            showMessage("Gelieve een titel mee te geven")
            return false
        }
        if (beschrijving.isNullOrEmpty())
        {
            showMessage("Gelieve een beschrijving mee te geven")
            return false
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(emailadres).matches() and emailadres.isNotEmpty())
        {
            showMessage("gelieve een geldig emailadres op te geven")
            return false
        }
        if (!Patterns.WEB_URL.matcher(url).matches() and url.isNotEmpty())
        {
            showMessage("gelieve een geldige URL op te geven")
            return false
        }
        if (!Patterns.WEB_URL.matcher(chatUrl).matches() and chatUrl.isNotEmpty())
        {
            showMessage("gelieve een geldige chat URL op te geven")
            return false
        }
        if (!Patterns.PHONE.matcher(telefoonnummer).matches() and telefoonnummer.isNotEmpty())
        {
            showMessage("gelieve een geldige chat URL op te geven")
            return false
        }
        return true

    }

    private fun showMessage(text: String)
    {
        Log.d("Validation",text);
    }
}
