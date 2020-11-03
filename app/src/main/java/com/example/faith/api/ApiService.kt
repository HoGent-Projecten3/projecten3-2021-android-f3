package com.example.faith.api

import android.os.Message
import com.example.faith.data.*
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query
import java.util.*

/**
 * @author Remi Mestdagh
 */
interface ApiService {

    @Headers("No-Authentication: true")
    @POST("Account/login")
    fun login(@Body login: Login): Call<LoginResponse>

    @Multipart
    @POST("Cinema/imageFile")
    fun uploadMedia(
        @Part imageFile: MultipartBody.Part
    ): Call<Message>

    @POST("Cinema/text")
    fun uploadText(
        @Query("titel") titel: String,
        @Query("beschrijving") beschrijving: String
    ): Call<Message>

    @POST("Chat")
    fun verstuurBericht(
        @Query("verstuurder") verstuurder: Gebruiker?,
        @Query("text") text: String?,
        @Query("datum") datum: Date?
    ): Call<Message>

    @GET("Chat")
    fun getBerichten(): Call<List<Bericht>>

    @GET("Gebruiker")
    fun getGebruiker(): Call<Gebruiker>

    @GET("Cinema/Media")
    suspend fun getMedia(
        @Query("page") page: Int,
        @Query("aantal") perPage: Int
    ): ApiMediumSearchResponse
    @GET("Cinema/Media")
    fun getMedia2(
        @Query("page") page: Int,
        @Query("aantal") perPage: Int
    ): Call<ApiMediumSearchResponse>
    @GET("Cinema/Dagboek")
    suspend fun getDagboek(
        @Query("page") page: Int,
        @Query("aantal") perPage: Int
    ): ApiDagboekSearchResponse
    @GET("Cinema/Dagboek")
    fun getDagboek2(
        @Query("page") page: Int,
        @Query("aantal") perPage: Int
    ): Call<ApiDagboekSearchResponse>

    @GET("Cinema/id")
    fun getMedium(@Query("mediumId") id: Int): ApiMediumResponse

    data class LoginResponseModel(
        val token: String,
        val
        refreshToken: String
    )
}
