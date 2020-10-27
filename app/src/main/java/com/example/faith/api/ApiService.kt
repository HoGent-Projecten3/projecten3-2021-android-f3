package com.example.faith.api

import android.os.Message
import com.example.faith.data.*
import okhttp3.MultipartBody
import retrofit2.Call
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
    fun getGebruiker(): Call<Gebruiker>

    @Headers("Content-Type: application/json")
    @POST("Account/login")
    fun login(@Body login: Login): Call<LoginResponse>

    @GET("Cinema")
    suspend fun getMedia(): ApiSearchResponse
    @GET("Cinema")
    fun getMedia2(): Call<ApiSearchResponse>

    @GET("Cinema/id")
    fun getMedium(@Query("mediumId") id: Int): ApiMediumResponse

    data class LoginResponseModel(
        val token: String,
        val
        refreshToken: String
    )
}
