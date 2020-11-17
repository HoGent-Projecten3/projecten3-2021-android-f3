package com.example.faith.data

import com.example.faith.api.ApiService
import com.example.faith.api.MyServiceInterceptor
import retrofit2.Call
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GebruikerRepository @Inject constructor(
    private val service: ApiService,
    private val interceptor: MyServiceInterceptor
) {

    fun getGebruiker(): Call<Gebruiker> {
        return service.getGebruiker()
    }

    fun login(login: Login): Call<LoginResponse> {
        return service.login(login)
    }
}
