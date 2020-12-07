package com.example.faith.viewmodels

import android.util.Base64
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.faith.api.MyServiceInterceptor
import com.example.faith.data.GebruikerRepository
import com.example.faith.data.Login
import com.example.faith.data.LoginResponse
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.nio.charset.Charset


class LoginViewModel @ViewModelInject constructor(
        val repository: GebruikerRepository,
        val interceptor: MyServiceInterceptor
) : ViewModel() {

    private val _loginSuccesvol = MutableLiveData<Boolean>(false)
    private val _gebruikerEmail = MutableLiveData<String>()

    val gebruikerEmail: LiveData<String>
        get() = _gebruikerEmail

    val loginSuccesvol: LiveData<Boolean>
        get() = _loginSuccesvol

    private val _errorMessage = MutableLiveData<String>()

    val errorMessage: LiveData<String>
        get() = _errorMessage


    private fun getJson(strEncoded: String): String? {
        val decodedBytes: ByteArray = Base64.decode(strEncoded, Base64.DEFAULT);
        return String(decodedBytes, Charset.defaultCharset())
    }
    fun login(login: Login) {

        repository.login(login).enqueue(
                object : Callback<LoginResponse> {

                    override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {

                        if (response.isSuccessful) {
                            interceptor.setSessionToken(response.body()?.authToken)
                            val parsedToken = JSONObject(response.body()?.authToken?.split(".")?.get(1)?.let { getJson(it) })
                            val rol = parsedToken["http://schemas.microsoft.com/ws/2008/06/identity/claims/role"]
                            if(!rol.equals("Customer")){
                                _loginSuccesvol.value = false
                                _errorMessage.value = "Login failed"
                            } else{
                                _gebruikerEmail.value = parsedToken["unique_name"] as String
                                _loginSuccesvol.value = true
                            }

                        } else {
                            _loginSuccesvol.value = false
                            _errorMessage.value = "Login failed"
                        }
                    }

                    override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                        _errorMessage.value = "Failure: " + t.message
                    }
                }
        )
    }
    fun logout() {
        _loginSuccesvol.value = false
        _gebruikerEmail.value = ""
    }
}
