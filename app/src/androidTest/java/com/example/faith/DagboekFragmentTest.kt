package com.example.faith

import android.os.Bundle
import androidx.navigation.findNavController
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.example.faith.utilities.testMedium
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DagboekFragmentTest {


    @Rule
    @JvmField
    val activityTestRule = ActivityTestRule(MainActivity::class.java)

    @Before
    fun jumpToMediumDetailFragment() {
        activityTestRule.activity.apply {
            runOnUiThread {

                findNavController(R.id.navigation).navigate(R.id.dagboekFragment)
            }
        }
    }

    @Test
    fun testValidation() {
        

    }
}