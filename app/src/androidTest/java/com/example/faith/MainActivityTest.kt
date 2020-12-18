package com.example.faith

import android.app.Application
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.example.faith.api.ApiService
import com.example.faith.api.MyServiceInterceptor
import com.example.faith.api.SignalRService
import com.example.faith.data.Login
import com.example.faith.data.LoginResponse
import com.example.faith.di.DateSerializer
import com.example.faith.di.NetworkModule
import com.example.faith.utilities.MainTestRunner
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import org.junit.Rule
import org.junit.Test
import org.junit.rules.RuleChain
import org.junit.runner.RunWith
import org.mockito.Mockito
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.Date
import javax.inject.Singleton

@UninstallModules(NetworkModule::class)
@HiltAndroidTest
class MainActivityTest {

    @Module
    @InstallIn(ApplicationComponent::class)
     class TestModule() {

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
            val gsonBuilder = GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .registerTypeAdapter(
                    Date::class.java,
                    DateSerializer()
                )
            gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            return gsonBuilder.create()
        }

        @Provides
        @Singleton
        fun provideRetrofit(gson: Gson, okHttpClient: OkHttpClient?): Retrofit {
            return Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl("http://192.168.1.37:45455/api/")
                .client(okHttpClient)
                .build()
        }

        @Provides
        @Singleton
        fun providesNetworkService(retrofit: Retrofit): ApiService {
            return retrofit.create(ApiService::class.java)
        }
    }

    private val hiltRule = HiltAndroidRule(this)
    private val activityTestRule = ActivityTestRule(MainActivity::class.java)

    @get:Rule
    val rule = RuleChain
        .outerRule(hiltRule)
        .around(activityTestRule)

    @Test
    fun loginTest() {
        var email = "joost@kaas.be"

        Espresso.onView(ViewMatchers.withId(R.id.editTextTextEmailAddress))
            .perform(
                ViewActions.typeText(
                    email
                )
            )
        Espresso.closeSoftKeyboard()
        Espresso.onView(ViewMatchers.withId(R.id.editTextNumberPassword))
            .perform(ViewActions.typeText("P@ssword123"))
        Espresso.closeSoftKeyboard()
        Espresso.onView(ViewMatchers.withId(R.id.button_login)).perform(ViewActions.click())
    }
}