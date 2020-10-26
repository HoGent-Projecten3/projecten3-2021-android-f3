package com.example.faith.data

import com.example.faith.api.ApiService
import retrofit2.Call
import javax.inject.Inject

class GebruikerRepository @Inject constructor(private val service: ApiService,
                                              ) {

    fun getGebruiker(token:String): Call<Gebruiker> {
        return service.getGebruiker(token);
    }

    fun login(login: Login): Call<LoginResponse> {
        return service.login(login);
    }
    /*
    fun setToken(token: String){
        interceptor.setSessionToken(token)
    }

     */

}