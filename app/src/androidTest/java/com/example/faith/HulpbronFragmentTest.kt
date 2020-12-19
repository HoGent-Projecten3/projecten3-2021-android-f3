package com.example.faith

import android.content.Context
import android.content.res.Resources
import android.os.Message
import android.telecom.Call
import androidx.navigation.findNavController
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import com.example.faith.api.ApiService
import com.example.faith.api.SignalRService
import com.example.faith.data.*
import com.example.faith.viewmodels.ChatViewModel
import com.example.faith.viewmodels.HulpbronViewModel
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.RuleChain

@HiltAndroidTest
class HulpbronFragmentTest {
    private val hiltRule = HiltAndroidRule(this)
    private val activityTestRule = ActivityTestRule(MainActivity::class.java)
    private val apiService: ApiService = mock()
    private val appDB: AppDatabase = mock()
    var hulpbronDao: HulpbronDao = mock()
    var hulpbronRepository: HulpbronRepository = HulpbronRepository(hulpbronDao,apiService,appDB)
    var hulpbronViewModel: HulpbronViewModel = HulpbronViewModel(hulpbronRepository)
    private val message: Message = Message();


    @get:Rule
    val rule = RuleChain
        .outerRule(hiltRule)
        .around(activityTestRule)

    @Before
    fun initHulpbronFragment() {
        this.hulpbronViewModel = HulpbronViewModel(hulpbronRepository)
        activityTestRule.activity.apply {
            runOnUiThread {
                findNavController(R.id.myNavHostFragment).navigate(R.id.hulpbronFragment)
            }
        }
    }

    @After
    fun tearDown() {
    }

    @Test
    fun ValidInputNavigateToList() {
//        whenever(this.hulpbronViewModel.maakHulpbron("testtitel","testinhoud","","","","")).thenAnswer {
//            return@thenAnswer
//        } TODO: Mock call
        onView(withId(R.id.textInputTitel)).perform(ViewActions.typeText("testtitel"))
        Espresso.closeSoftKeyboard()
        onView(withId(R.id.textInputBeschrijving)).perform(ViewActions.typeText("testinhoud"))
        Espresso.closeSoftKeyboard()
        Espresso.onView(ViewMatchers.withId(R.id.btnSaveHulpbron)).perform(ViewActions.click())
//        Espresso.onView(withId(R.layout.fragment_hulpbron_list)).check(matches(isDisplayed())) TODO: Check if displayed
    }

    @Test
    fun InvalidEmailShowsSnackbar() {
        onView(withId(R.id.textInputTitel)).perform(ViewActions.typeText("testtitel"))
        Espresso.closeSoftKeyboard()
        onView(withId(R.id.textInputBeschrijving)).perform(ViewActions.typeText("testinhoud"))
        Espresso.closeSoftKeyboard()
        onView(withId(R.id.textInputEmailadres)).perform(ViewActions.typeText("ongeldigEmailadres"))
        Espresso.closeSoftKeyboard()
        Espresso.onView(ViewMatchers.withId(R.id.btnSaveHulpbron)).perform(ViewActions.click())
        onView(withText("Gelieve een geldig emailadres op te geven"))
            .check(matches(isDisplayed()));
    }

    @Test
    fun InvalidUrlShowsSnackbar() {
        onView(withId(R.id.textInputTitel)).perform(ViewActions.typeText("testtitel"))
        Espresso.closeSoftKeyboard()
        onView(withId(R.id.textInputBeschrijving)).perform(ViewActions.typeText("testinhoud"))
        Espresso.closeSoftKeyboard()
        onView(withId(R.id.textInputUrl)).perform(ViewActions.typeText("ongeldigeUrl"))
        Espresso.closeSoftKeyboard()
        Espresso.onView(ViewMatchers.withId(R.id.btnSaveHulpbron)).perform(ViewActions.click())
        onView(withText("Gelieve een geldige url op te geven"))
            .check(matches(isDisplayed()));
    }

    @Test
    fun InvalidChatUrlShowsSnackbar() {
        onView(withId(R.id.textInputTitel)).perform(ViewActions.typeText("testtitel"))
        Espresso.closeSoftKeyboard()
        onView(withId(R.id.textInputBeschrijving)).perform(ViewActions.typeText("testinhoud"))
        Espresso.closeSoftKeyboard()
        onView(withId(R.id.textInputChatUrl)).perform(ViewActions.typeText("ongeldigeChatUrl"))
        Espresso.closeSoftKeyboard()
        Espresso.onView(ViewMatchers.withId(R.id.btnSaveHulpbron)).perform(ViewActions.click())
        onView(withText("Gelieve een geldige chat url op te geven"))
            .check(matches(isDisplayed()));
    }

    @Test
    fun InvalidTelefoonnummerShowsSnackbar() {
        onView(withId(R.id.textInputTitel)).perform(ViewActions.typeText("testtitel"))
        Espresso.closeSoftKeyboard()
        onView(withId(R.id.textInputBeschrijving)).perform(ViewActions.typeText("testinhoud"))
        Espresso.closeSoftKeyboard()
        onView(withId(R.id.textInputTelefoonnummer)).perform(ViewActions.typeText("05456+2354"))
        Espresso.closeSoftKeyboard()
        Espresso.onView(ViewMatchers.withId(R.id.btnSaveHulpbron)).perform(ViewActions.click())
        onView(withText("Gelieve een geldig telefoonnummer op te geven"))
            .check(matches(isDisplayed()));
    }

}