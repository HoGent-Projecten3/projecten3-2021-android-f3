package com.example.faith.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.faith.api.MyServiceInterceptor
import com.example.faith.data.GebruikerRepository
import com.example.faith.data.Login
import com.example.faith.data.LoginResponse
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel @ViewModelInject constructor(
    val repository: GebruikerRepository, val interceptor: MyServiceInterceptor
) : ViewModel() {

    private val _loginSuccesvol = MutableLiveData<Boolean>()

    val loginSuccesvol: LiveData<Boolean>
        get() = _loginSuccesvol

    private val _errorMessage = MutableLiveData<String>()

    val errorMessage: LiveData<String>
        get() = _errorMessage

    fun login(login: Login){

        repository.login(login).enqueue(object : Callback<ResponseBody> {

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {

                if (response.isSuccessful) {
                    interceptor.setSessionToken(response.body().toString())
                    _loginSuccesvol.value = true

                } else {
                    _loginSuccesvol.value = false
                    _errorMessage.value = "Login failed"
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                _errorMessage.value = "Failure: " + t.message
            }

        })
    }

}