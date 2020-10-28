package com.example.faith.api

import android.os.Message
import com.example.faith.data.ApiDagboekSearchResponse
import com.example.faith.data.ApiMediumResponse
import com.example.faith.data.ApiMediumSearchResponse
import com.example.faith.data.Gebruiker
import com.example.faith.data.Login
import com.example.faith.data.LoginResponse
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query
import retrofit2.http.Url

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

    @GET("Cinema/Media")
    suspend fun getMedia(): ApiMediumSearchResponse
    @GET("Cinema/Media")
    fun getMedia2(): Call<ApiMediumSearchResponse>
    @GET("Cinema/Dagboek")
    suspend fun getDagboek() : ApiDagboekSearchResponse
    @GET("Cinema/Dagboek")
    fun getDagboek2() : Call<ApiDagboekSearchResponse>

    @GET("Cinema/id")
    fun getMedium(@Query("mediumId") id: Int): ApiMediumResponse

    data class LoginResponseModel(
        val token: String,
        val
        refreshToken: String
    )
}
