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

class LoginViewModel : ViewModel() {

    private val _loginSuccesvol = MutableLiveData<Boolean>()

    val loginSuccesvol: LiveData<Boolean>
        get() = _loginSuccesvol

    private val _errorMessage = MutableLiveData<String>()

    val errorMessage: LiveData<String>
        get() = _errorMessage

    fun login(login: Login){

        LoginApi.retrofitService.login(login).enqueue(object : Callback<ResponseBody> {

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {

                if (response.isSuccessful) {
                    val token = "Bearer " + response.body().string()
                    JWTTokenStarage.JWTToken = token
                    _loginSuccesvol.value = true
                    _loginSuccesvol.value = false
                } else {
                    _errorMessage.value = "Login failed"
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                _errorMessage.value = "Failure: " + t.message
            }

        })
    }

}