package com.example.faith

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.faith.databinding.FragmentHoofdschermBinding
import com.example.faith.databinding.FragmentLoginBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HoofdschermFragment : Fragment() {

    lateinit var binding: FragmentHoofdschermBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_hoofdscherm, container,false)

        getGebruiker()

        return binding.root
    }

    fun getGebruiker() {
        LoginApi.retrofitService.getGebruiker(JWTTokenStarage.JWTToken).enqueue(object :
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