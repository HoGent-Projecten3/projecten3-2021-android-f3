package com.example.faith.di

import android.app.Application
import com.example.faith.api.ApiService
import com.example.faith.api.MyServiceInterceptor
import com.example.faith.api.SignalRService
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * @author Remi Mestdagh
 */
@Module
@InstallIn(ApplicationComponent::class)
class NetworkModule() {

    @Provides
    @Singleton
    fun provideHttpCache(application: Application): Cache {
        val cacheSize = 10 * 1024 * 1024
        return Cache(application.cacheDir, cacheSize.toLong())
    }

    @Provides
    @Singleton
    fun provideSignalRService(): SignalRService {
        return SignalRService()
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        myServiceInterceptor: MyServiceInterceptor,

        cache: Cache
    ): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        val client = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .addInterceptor(myServiceInterceptor)
            .cache(cache).build()
        client
            .newBuilder()
            .addInterceptor(
                Interceptor { chain: Interceptor.Chain ->
                    val original: Request = chain.request()
                    val requestBuilder: Request.Builder = original.newBuilder()
                        .addHeader("Accept", "Application/JSON")
                    val request: Request = requestBuilder.build()
                    chain.proceed(request)
                }
            ).build()
        return client
    }

    @Provides
    @Singleton
    fun provideGson(): Gson {
        val gsonBuilder = GsonBuilder()
        gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
        return gsonBuilder.create()
    }
    @Provides
    @Singleton
    fun provideRetrofit(gson: Gson, okHttpClient: OkHttpClient?): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl("http://10.0.0.10:45455/api/")
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    fun providesNetworkService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }
}
