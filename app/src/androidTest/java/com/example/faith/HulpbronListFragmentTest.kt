package com.example.faith

import android.app.Application
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.findNavController
import androidx.paging.ExperimentalPagingApi
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.rule.ActivityTestRule
import com.example.faith.api.ApiService
import com.example.faith.api.MyServiceInterceptor
import com.example.faith.api.SignalRService
import com.example.faith.data.*
import com.example.faith.di.DateSerializer
import com.example.faith.di.NetworkModule
import com.example.faith.utilities.RecyclerViewItemCountAssertion
import com.example.faith.viewmodels.HulpbronListViewModel
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.testing.BindValue
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import kotlinx.coroutines.runBlocking
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.RuleChain
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.Date
import javax.inject.Singleton

@HiltAndroidTest
@UninstallModules(NetworkModule::class)
class HulpbronListFragmentTest {

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
                    .baseUrl("https://test")
                    .client(okHttpClient)
                    .build()
        }
    }

    private val hiltRule = HiltAndroidRule(this)
    private val activityTestRule = ActivityTestRule(MainActivity::class.java)

    @get:Rule
    val rule = RuleChain
            .outerRule(hiltRule)
            .around(activityTestRule)

    @Before
    fun init() {
        hiltRule.inject()
        activityTestRule.activity.apply {
            runOnUiThread {
                findNavController(R.id.myNavHostFragment).navigate(R.id.hulpbronListFragment)
            }
        }
    }

    @BindValue
    @JvmField
    var mockRepo: ApiService = mock()


    var hulpbron1 = Hulpbron(1, "TESTTITEL", "TESTINHOUD", "WWW.TEST.BE", "123", "TEST@TEST.COM", "WWW.TEST.BE", Date(), "Admin")
    var hulpbronnen = listOf(hulpbron1)
    var hulpbronResponse = ApiHulpbronSearchResponse(hulpbronnen, 0, "0", "1")


    @After
    fun tearDown() {
    }

    @ExperimentalPagingApi
    @Test
    fun showList() {
        runBlocking {
            whenever(mockRepo.getHulpbronnen(any(), any(), any(), any(), any())).thenReturn(hulpbronResponse)
        }
        onView(withId(R.id.hulpbron_list))
                .check(RecyclerViewItemCountAssertion(1));
    }
}