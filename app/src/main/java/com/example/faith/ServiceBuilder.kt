package com.example.faith

import okhttp3.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import javax.net.SocketFactory
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

object ServiceBuilder {
    private val client = OkHttpClient.Builder()
        .connectionSpecs(listOf(ConnectionSpec.COMPATIBLE_TLS))
        .build();


    private val retrofit = Retrofit.Builder()
        .baseUrl("https://10.0.2.2:5001/")
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()



    fun<T> buildService(service: Class<T>): T{
        return retrofit.create(service)
    }
}