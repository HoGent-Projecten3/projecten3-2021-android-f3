package com.example.faith.data

import com.example.faith.api.ApiService
import com.example.faith.api.MyServiceInterceptor
import okhttp3.ResponseBody
import retrofit2.Call
import javax.inject.Inject

class GebruikerRepository @Inject constructor(private val service: ApiService, private val interceptor: MyServiceInterceptor
                                              ) {

    fun getGebruiker(): Call<Gebruiker> {
        return service.getGebruiker();
    }

    fun login(login: Login): Call<ResponseBody> {
        return service.login(login);
    }
    /*
    fun setToken(token: String){
        interceptor.setSessionToken(token)
    }

     */

}
