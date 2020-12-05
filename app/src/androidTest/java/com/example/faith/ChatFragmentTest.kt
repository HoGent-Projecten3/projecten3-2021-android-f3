package com.example.faith

import android.os.Bundle
import androidx.navigation.findNavController
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.example.faith.utilities.testMedium
import dagger.hilt.android.testing.HiltAndroidRule
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.RuleChain
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ChatFragmentTest {

    private val hiltRule = HiltAndroidRule(this)
    private val activityTestRule = ActivityTestRule(MainActivity::class.java)

    @get:Rule
    val rule = RuleChain
            .outerRule(hiltRule)
            .around(activityTestRule)

    @Test
    fun clickOpenChat_OpensChat() {
        // Given that no Plants are added to the user's garden

        // When the "Add Plant" button is clicked
        Espresso.onView(withId(R.id.action_hotelFragment_to_chatFragment)).perform(ViewActions.click())

        // Then the ViewPager should change to the Plant List page
        Espresso.onView(withId(R.id.chatFragment)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }
}