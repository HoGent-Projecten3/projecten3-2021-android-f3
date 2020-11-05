package com.example.faith.api

import android.os.Message
import com.example.faith.data.*
import okhttp3.MultipartBody
import retrofit2.Call
import com.example.faith.data.ApiDagboekSearchResponse
import com.example.faith.data.ApiMediumResponse
import com.example.faith.data.ApiMediumSearchResponse
import com.example.faith.data.Gebruiker
import com.example.faith.data.Login
import com.example.faith.data.LoginResponse
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level
import retrofit2.Call;
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query
import java.time.LocalDateTime

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

    @POST("Chat/PostBericht")
    fun verstuurBericht(
        @Query("verstuurderEmail") verstuurderEmail: String,
        @Query("ontvangerEmail") ontvangerEmail: String,
        @Query("verstuurderNaam") verstuurderNaam: String,
        @Query("ontvangerNaam") ontvangerNaam: String,
        @Query("text") text: String

    ): Call<Message>

    @GET("Chat/GetBerichtenMetBegeleider")
    suspend fun getBerichten(
        @Query("page") page:Int,
        @Query("aantal") perPage: Int
    ): ApiBerichtSearchResponse

    @GET("Chat/GetBerichtenMetBegeleider")
    fun getBerichten2(
        @Query("page") page:Int,
        @Query("aantal") perPage: Int
    ): Call<ApiBerichtSearchResponse>

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



    @GET("Client/GetDoelen")
    fun getDoelen():Call<List<DoelDTO>>

    @Headers("Content-Type: application/json")
    @POST("Client/PostDoelen")
    fun postDoelen(@Body doelenDTO: List<DoelDTO>): Call<Boolean>

    @Headers("Content-Type: application/json")
    @POST("Client/SyncDoelen")
    fun syncDoelen(@Body doelenDTO: List<DoelDTO>): Call<List<DoelDTO>>


    @GET("Cinema/id")
    fun getMedium(@Query("mediumId") id: Int): ApiMediumResponse

    data class LoginResponseModel(
        val token: String,
        val
        refreshToken: String
        val token: String
        , val refreshToken: String
    )
}
