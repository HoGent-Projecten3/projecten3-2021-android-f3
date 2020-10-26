package com.example.faith.api

import android.os.Message
import com.example.faith.data.ApiSearchResponse
import com.example.faith.data.Gebruiker
import com.example.faith.data.Login
import com.example.faith.data.LoginResponse
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level
import retrofit2.Call;
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*


interface ApiService {

    @Multipart
    @POST
    fun uploadMedia(
        @Url url: String,
        @Part imageFile: MultipartBody.Part
    ): Call<Message>

    @POST
    fun uploadText(@Url url: String, @Body s: String): Call<Message>


    @Headers("No-Authentication: true")
    @POST
    fun login(@Url account: String, @Body login: Login): Call<LoginResponse>


    @GET("Gebruiker")
    fun getGebruiker(@Header("Authorization") token: String): Call<Gebruiker>

    @Headers("Content-Type: application/json")
    @POST("Account/login")
    fun login(@Body login: Login): Call<LoginResponse>

    @GET("Cinema")
    suspend fun getMedia(
    ): ApiSearchResponse




    data class LoginResponseModel(
        val token: String, val
        refreshToken: String
    )
}

