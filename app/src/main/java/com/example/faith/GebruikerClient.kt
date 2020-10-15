package com.example.faith

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

public interface GebruikerClient {

    @POST("account")
    fun login(@Body login: Login): Call<Gebruiker>;

    @GET("secretinfo")
    fun getData(@Header("api_key") key: String): Call<Gebruiker>


}