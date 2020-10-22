package com.example.faith

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel: ViewModel(){

    private var JWTToken: String = ""

    private val _response = MutableLiveData<String>()

    val response: LiveData<String>
        get() = _response

    fun login(login: Login){
        LoginApi.retrofitService.login(login).enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                val token = "Bearer " + response.body().string()
                _response.value = token
                JWTToken = token
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                _response.value = "Failure: " + t.message
            }

        })
    }

    fun getHello() {
        LoginApi.retrofitService.getHelloWorld(JWTToken).enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                _response.value = "Failure: " + t.message
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.body() != null) {
                    _response.value = response.body().string()
                }else{
                    _response.value = "${response.code().toString()}: ${response.message()}, ${response.raw()}"
                }
            }
        })
    }
}