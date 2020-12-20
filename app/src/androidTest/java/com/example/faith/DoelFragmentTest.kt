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
class DoelFragmentTest {
    private val hiltRule = HiltAndroidRule(this)
    private val activityTestRule = ActivityTestRule(MainActivity::class.java)


    @get:Rule
    val rule = RuleChain
        .outerRule(hiltRule)
        .around(activityTestRule)

    @Before
    fun initHulpbronFragment() {
        activityTestRule.activity.apply {
            runOnUiThread {
                findNavController(R.id.myNavHostFragment).navigate(R.id.penthouseFragment)
            }
        }
    }

    @Test
    fun ValidInputDisplaysButton() {
        Espresso.onView(ViewMatchers.withId(R.id.main_add_button)).perform(ViewActions.click())
        Espresso.onView(withId(R.id.main_add_confirm_button)).check(matches(isDisplayed()))
    }
    @Test
    fun LongInputDisplaysSnackbar() {
        Espresso.onView(ViewMatchers.withId(R.id.main_add_button)).perform(ViewActions.click())
        onView(withId(R.id.main_add_edit_text)).perform(ViewActions.typeText("Loooooooooooooooong"))
        Espresso.closeSoftKeyboard()
        Espresso.onView(ViewMatchers.withId(R.id.main_add_confirm_button)).perform(ViewActions.click())
        onView(withText("Inhoud mag niet langer dan 15 tekens zijn!"))
            .check(matches(isDisplayed()));
    }

    @Test
    fun EmptyInputDisplaysSnackbar() {
        Espresso.onView(ViewMatchers.withId(R.id.main_add_button)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.main_add_confirm_button)).perform(ViewActions.click())
        onView(withText("Inhoud mag niet leeg zijn!"))
            .check(matches(isDisplayed()));

    }



}