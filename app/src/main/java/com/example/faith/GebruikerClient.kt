package com.example.faith

import retrofit2.Call
import retrofit2.http.*

public interface GebruikerClient {

    @Headers("Content-Type: application/json")
    @POST("account")
    fun login(@Body login: Login): Call<Gebruiker>



}