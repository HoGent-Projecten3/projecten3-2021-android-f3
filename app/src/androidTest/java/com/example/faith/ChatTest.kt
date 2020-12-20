package com.example.faith

import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.rule.ActivityTestRule
import com.example.faith.api.ApiService
import com.example.faith.api.MyServiceInterceptor
import com.example.faith.api.SignalRService
import com.example.faith.data.ApiBerichtSearchResponse
import com.example.faith.data.AppDatabase
import com.example.faith.data.Bericht
import com.example.faith.data.BerichtDao
import com.example.faith.data.BerichtRepository
import com.example.faith.data.GebruikerRepository
import com.example.faith.utilities.RecyclerViewItemCountAssertion
import com.example.faith.viewmodels.ChatViewModel
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import java.time.LocalDateTime
import java.util.*
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.spy
import com.nhaarman.mockitokotlin2.whenever
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.runBlocking
import org.junit.Rule
import org.junit.rules.RuleChain
import java.util.regex.Pattern.matches

@HiltAndroidTest
class ChatTest {

    var datum = LocalDateTime.now().toString()
    var bericht1 = Bericht(0, "", "", "", "", "", Date())
    var berichten = listOf(bericht1)
    var berichtResponse = ApiBerichtSearchResponse(berichten, datum, 20)
    val activityTestRule = ActivityTestRule(MainActivity::class.java)
    private val hiltRule = HiltAndroidRule(this)

    @get:Rule
    val rule = RuleChain
        .outerRule(hiltRule)
        .around(activityTestRule)

    private val apiService: ApiService = mock()
    private val signalRService: SignalRService = SignalRService()
    private val appDB: AppDatabase = mock()
    var berichtDao: BerichtDao = mock()
    var interceptor: MyServiceInterceptor = MyServiceInterceptor(signalRService)
    var gebruikerRepository: GebruikerRepository = GebruikerRepository(apiService, interceptor)
    var berichtRepo: BerichtRepository = BerichtRepository(apiService, berichtDao)

    var chatViewModel: ChatViewModel = ChatViewModel(gebruikerRepository, berichtRepo)

    @Before
    fun setup() {
        this.chatViewModel = ChatViewModel(gebruikerRepository, berichtRepo)
        activityTestRule.activity.apply {
            runOnUiThread {
                findNavController(R.id.myNavHostFragment).navigate(R.id.chatFragment)
            }
        }
    }

    @Test
    fun verstuurBericht() {
        whenever(this.berichtRepo.getBerichten2(datum, 20)).thenAnswer {
            return@thenAnswer berichtResponse
        }
        whenever(this.berichtRepo.verstuurBericht("", "", "", "", "")).thenAnswer {
            return@thenAnswer bericht1
        }
        onView(withId(R.id.txfEditBericht)).perform(ViewActions.typeText("test"))
        onView(withId(R.id.btSendMessage)).perform(click())

        onView(withId(R.id.messages_list))
                .check(RecyclerViewItemCountAssertion(1));
    }
}
