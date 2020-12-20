package com.example.faith

import android.os.Bundle
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.ext.junit.rules.activityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.faith.api.ApiService
import com.example.faith.data.Login
import com.example.faith.data.LoginResponse
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import retrofit2.Response
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.rule.ActivityTestRule
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.rules.RuleChain
import org.mockito.junit.MockitoJUnitRunner

@HiltAndroidTest
class DagboekFragmentTest {

    private val hiltRule = HiltAndroidRule(this)
    private val activityTestRule = ActivityTestRule(MainActivity::class.java)

    @get:Rule
    val rule = RuleChain
        .outerRule(hiltRule)
        .around(activityTestRule)

    @Before
    fun jumpToDagboek() {
        activityTestRule.activity.apply {
            runOnUiThread {
                findNavController(R.id.myNavHostFragment).navigate(R.id.dagboekFragment)
            }
        }
    }

    @After
    fun tearDown() {
    }

    @Test
    fun testInput() {
        onView(withId(R.id.textInputTitelDagboek)).perform(ViewActions.typeText("P@ssword123"))
        Espresso.closeSoftKeyboard()
        onView(withId(R.id.textInputDescription)).perform(ViewActions.typeText("tekst"))
        Espresso.closeSoftKeyboard()
        onView(withId(R.id.btSaveDagboek)).perform(ViewActions.click())
    }

}