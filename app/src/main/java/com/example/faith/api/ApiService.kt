package com.example.faith.api

import android.os.Message
import com.example.faith.data.*
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query

/**
 * @author Remi Mestdagh
 */
interface ApiService {

    @Multipart
    @POST("Cinema/imageFile")
    fun uploadMedia(
        @Part imageFile: MultipartBody.Part,
        @Query("beschrijving")beschrijving: String?
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
        @Query("page") page: Int,
        @Query("aantal") perPage: Int
    ): ApiBerichtSearchResponse

    @GET("Chat/GetBerichtenMetBegeleider")
    fun getBerichten2(
        @Query("page") page: Int,
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

    @GET("Infobalie/getHulpbronnenPaging")
    suspend fun getHulpbronnen(
        @Query("page") page:Int,
        @Query("aantal") perPage:Int
    ) : ApiHulpbronSearchResponse

    @GET("Infobalie/getHulpbronnenPaging")
    fun getHulpbronnen2(
        @Query("page") page:Int,
        @Query("aantal") perPage:Int
    ) : Call<ApiHulpbronSearchResponse>

    @POST("Account/login")
    fun login(@Body login: Login): Call<LoginResponse>


    @GET("Client/GetDoelen")
    fun getDoelen(): Call<List<Doel>>

    @Headers("Content-Type: application/json")
    @POST("Client/PostDoelen")
    fun postDoelen(@Body doelen: List<Doel>): Call<Boolean>

    @Headers("Content-Type: application/json")
    @POST("Client/SyncDoelen")
    fun syncDoelen(@Body doelen: List<Doel>): Call<List<Doel>>

    @GET("Cinema/id")
    fun getMedium(@Query("mediumId") id: Int): ApiMediumResponse

    @DELETE("Cinema")
    fun removeMedium(@Query("mediumId") id: Int): Call<Message>

    data class LoginResponseModel(
        val token: String,
        val
        refreshToken: String
    )
}
