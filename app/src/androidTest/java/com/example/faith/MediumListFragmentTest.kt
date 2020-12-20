package com.example.faith

import android.app.Application
import androidx.navigation.findNavController
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.rule.ActivityTestRule
import com.example.faith.api.ApiService
import com.example.faith.api.MyServiceInterceptor
import com.example.faith.api.SignalRService
import com.example.faith.data.ApiMediumSearchResponse
import com.example.faith.data.Medium
import com.example.faith.data.MediumRepository
import com.example.faith.di.DateSerializer
import com.example.faith.di.NetworkModule
import com.example.faith.utilities.RecyclerViewItemCountAssertion
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
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.RuleChain
import org.mockito.Mock
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.Date
import javax.inject.Inject
import javax.inject.Singleton

@HiltAndroidTest
@UninstallModules(NetworkModule::class)
class MediumListFragmentTest {



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
                .baseUrl("https://f3backend-dev-as.azurewebsites.net/api/")
                .client(okHttpClient)
                .build()
        }

    }




    var medium = Medium(
        1,
        "TESTTITEL",
        "TESTINHOUD",
        "WWW.TEST.BE",
        1,
        Date()
    )
    var media = listOf(medium)
    private var response  = ApiMediumSearchResponse(media,0,"0","-1")
    private val hiltRule = HiltAndroidRule(this)
    private val activityTestRule = ActivityTestRule(MainActivity::class.java)
    @get:Rule
    val rule = RuleChain
        .outerRule(hiltRule)
        .around(activityTestRule)
    @Before
    fun init(){
        hiltRule.inject()
    }
    @BindValue @JvmField
    var mockRepo: ApiService = mock()

    @ExperimentalPagingApi
    @Test
    fun test1(){
        runBlocking {
            //whenever(mockRepo.getSearchResultStream(any())).thenReturn(flowOf(PagingData.empty()))
            whenever(mockRepo.getMedia(any(),any())).thenReturn(response)

        }

        ActivityScenario.launch(MainActivity::class.java)
        activityTestRule.activity.apply {
            runOnUiThread {
                findNavController(R.id.myNavHostFragment).navigate(R.id.mediumListFragment)
            }
        }
        Espresso.onView(ViewMatchers.withId(R.id.medium_list))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(R.id.medium_list))
            .check(RecyclerViewItemCountAssertion(1));
    }

}