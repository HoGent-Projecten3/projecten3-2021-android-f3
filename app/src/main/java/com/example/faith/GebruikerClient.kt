package com.example.faith

import retrofit2.Call
import retrofit2.http.*

interface GebruikerClient {

    @Headers("Content-Type: application/json")
    @POST
    fun login(@Url account:String,@Body login: Login): Call<Gebruiker>



}