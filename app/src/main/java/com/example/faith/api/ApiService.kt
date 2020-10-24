package com.example.faith.api
import android.os.Message
import com.example.faith.data.Gebruiker
import com.example.faith.data.Login
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level
import retrofit2.Call;
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*


interface ApiService {

    @Multipart
    @POST
    fun uploadMedia( @Url url:String,
        @Part imageFile: MultipartBody.Part
    ) : Call<Message>

    @POST
    fun uploadText(@Url url:String,@Body s:String):Call<Message>


    @Headers("Content-Type: application/json")
    @POST
    fun login(@Url account:String,@Body login: Login): Call<Gebruiker>

    @GET("Gebruiker")
    fun getGebruiker(@Header("Authorization")token: String ): Call<Gebruiker>

    @Headers("Content-Type: application/json")
    @POST("Account/login")
    fun login(@Body login: Login): Call<ResponseBody>

    companion object {
        private const val BASE_URL = "http://192.168.1.37:45455/api/"

        fun create(): ApiService {
            val logger = HttpLoggingInterceptor().apply { level = Level.BASIC }

            val client = OkHttpClient.Builder()
                .addInterceptor(logger)
                .build()

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiService::class.java)
        }
    }

}