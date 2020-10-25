package com.example.faith

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.faith.api.ApiService
import com.example.faith.data.Gebruiker
import com.example.faith.data.JWTTokenStarage
import com.example.faith.databinding.FragmentHoofdschermBinding
import dagger.hilt.android.AndroidEntryPoint
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject
@AndroidEntryPoint
class HoofdschermFragment : Fragment() {

    lateinit var binding: FragmentHoofdschermBinding
    @Inject lateinit var service : ApiService;

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_hoofdscherm, container,false)

        getGebruiker()

        return binding.root
    }

    fun getGebruiker() {
        service.getGebruiker(JWTTokenStarage.JWTToken).enqueue(object :
            Callback<Gebruiker> {
            override fun onFailure(call: Call<Gebruiker>, t: Throwable) {
                //_response.value = "Failure: " + t.message
            }

            override fun onResponse(call: Call<Gebruiker>, response: Response<Gebruiker>) {
                if (response.isSuccessful) {
                    val gebruiker: Gebruiker? = response.body()
                    binding.helloText.text = "Welkom ${gebruiker?.voornaam} ${gebruiker?.achternaam}"
                }else{
                    //_response.value = "${response.code().toString()}: ${response.message()}"
                }
            }
        })
    }
}