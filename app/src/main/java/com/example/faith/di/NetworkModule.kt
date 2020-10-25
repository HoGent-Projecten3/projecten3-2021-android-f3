package com.example.faith.di

import MyServiceInterceptor
import com.example.faith.api.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(ApplicationComponent::class)
class NetworkModule() {
  @Provides
  @Singleton
  fun provideApi(): ApiService{
      return ApiService.create()
  }
}