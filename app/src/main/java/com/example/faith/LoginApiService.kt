package com.example.faith

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.*

private const val BASE_URL = "http://192.168.0.187:45455/api/"

private val retrofit = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create())
    .addConverterFactory(ScalarsConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()

interface LoginApiService {

    @GET("Gebruiker")
    fun getGebruiker(@Header("Authorization")token: String ): Call<Gebruiker>

    @Headers("Content-Type: application/json")
    @POST("Account/login")
    fun login(@Body login: Login): Call<ResponseBody>


}

object LoginApi{
    val retrofitService: LoginApiService by lazy { retrofit.create(LoginApiService::class.java) }
}