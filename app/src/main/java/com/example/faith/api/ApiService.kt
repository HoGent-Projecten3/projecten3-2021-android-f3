package com.example.faith.api

import android.os.Message
import com.example.faith.data.ApiBerichtSearchResponse
import com.example.faith.data.ApiHulpbronSearchResponse
import com.example.faith.data.ApiMediumSearchResponse
import com.example.faith.data.ApiTalentSearchResponse
import com.example.faith.data.Bericht
import com.example.faith.data.Doel
import com.example.faith.data.Gebruiker
import com.example.faith.data.HulpbronDTO
import com.example.faith.data.Login
import com.example.faith.data.LoginResponse
import com.example.faith.data.Medium
import com.example.faith.data.Talent
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
        @Query("beschrijving") beschrijving: String?
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

    ): Call<Bericht>

    @GET("Chat/GetBerichtenMetBegeleider")
    fun getBerichten(
        @Query("totDatum") totDatum: String,
        @Query("aantal") aantal: Int
    ): Call<ApiBerichtSearchResponse>

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

    @GET("Cinema/Dagboek")
    suspend fun getDagboek(
        @Query("page") page: Int,
        @Query("aantal") perPage: Int
    ): ApiMediumSearchResponse

    @GET("Infobalie/getHulpbronnen")
    suspend fun getHulpbronnen(
        @Query("textFilter") textFilter: String,
        @Query("includePublic") includePublic: Boolean,
        @Query("includePrivate") includePrivate: Boolean,
        @Query("page") page: Int,
        @Query("aantal") perPage: Int
    ): ApiHulpbronSearchResponse

    @DELETE("Infobalie")
    fun deleteHulpbron(@Query("hulpbronId") id: Int): Call<Boolean>

    @POST("Infobalie")
    fun postHulpbron(@Body hulpbron: HulpbronDTO): Call<Message>

    @GET("Client/GetDoelen")
    fun getDoelen(): Call<List<Doel>>

    @Headers("Content-Type: application/json")
    @POST("Client/PostDoelen")
    fun postDoelen(@Body doelen: List<Doel>): Call<Boolean>

    @Headers("Content-Type: application/json")
    @POST("Client/SyncDoelen")
    fun syncDoelen(@Body doelen: List<Doel>): Call<List<Doel>>

    @DELETE("Cinema")
    fun removeMedium(@Query("mediumId") id: Int): Call<Medium>

    @POST("Account/login")
    fun login(@Body login: Login): Call<LoginResponse>

    @GET("Client/GetTrofeesPaging")
    suspend fun getTalenten(
        @Query("page") page: Int,
        @Query("aantal") perPage: Int
    ): ApiTalentSearchResponse

    @GET("Client/GetGedeeldeTrofee")
    suspend fun getGedeeldeTalenten(
        @Query("page") page: Int,
        @Query("aantal") perPage: Int
    ): ApiTalentSearchResponse

    @POST("Client/Talent")
    fun postTalent(@Query("inhoud") inhoud: String): Call<Message>

    @DELETE("Client/VerwijderItem")
    fun removeTalent(@Query("id") id: Int): Call<Message>

    @GET("Client/GetItem")
    fun getItem(@Query("id") id: Int): Call<Talent>
}
