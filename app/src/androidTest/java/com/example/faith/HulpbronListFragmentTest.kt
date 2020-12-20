package com.example.faith

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.findNavController
import androidx.paging.ExperimentalPagingApi
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.rule.ActivityTestRule
import com.example.faith.api.ApiService
import com.example.faith.data.ApiHulpbronSearchResponse
import com.example.faith.data.AppDatabase
import com.example.faith.data.Hulpbron
import com.example.faith.data.HulpbronDao
import com.example.faith.data.HulpbronRepository
import com.example.faith.utilities.RecyclerViewItemCountAssertion
import com.example.faith.viewmodels.HulpbronListViewModel
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.RuleChain
import java.util.Date

@HiltAndroidTest
class HulpbronListFragmentTest {
    private val hiltRule = HiltAndroidRule(this)
    private val activityTestRule = ActivityTestRule(MainActivity::class.java)
    private val apiService: ApiService = mock()
    private val appDB: AppDatabase = mock()
    var hulpbronDao: HulpbronDao = mock()
    var savedStateHandel: SavedStateHandle = SavedStateHandle();
    var hulpbronRepository: HulpbronRepository = HulpbronRepository(hulpbronDao,apiService,appDB)
    var hulpbronListViewModel: HulpbronListViewModel = HulpbronListViewModel(hulpbronRepository,savedStateHandel)

    var hulpbron1 = Hulpbron(1, "TESTTITEL", "TESTINHOUD", "WWW.TEST.BE", "123", "TEST@TEST.COM", "WWW.TEST.BE", Date(),"Admin")
    var hulpbronnen = listOf(hulpbron1)
    var hulpbronResponse = ApiHulpbronSearchResponse(hulpbronnen, 0, "0","1")

    @get:Rule
    val rule = RuleChain
        .outerRule(hiltRule)
        .around(activityTestRule)

    @Before
    fun initHulpbronFragment() {
        this.hulpbronRepository =  HulpbronRepository(hulpbronDao,apiService,appDB)
        this.hulpbronListViewModel = HulpbronListViewModel(hulpbronRepository,savedStateHandel)
        activityTestRule.activity.apply {
            runOnUiThread {
                findNavController(R.id.myNavHostFragment).navigate(R.id.hulpbronListFragment)
            }
        }
    }

    @After
    fun tearDown() {
    }

    @ExperimentalPagingApi
    @Test
    fun ValidInputNavigateToList() {
//        whenever(this.hulpbronRepository.getHulpbronnen("", true, true, "hulpbron")).thenReturn(flowOf(pagingData))
        runBlocking{
            whenever(apiService.getHulpbronnen("", true, true, 0,20)).thenReturn(hulpbronResponse)
        }
        onView(withId(R.id.hulpbron_list))
            .check(RecyclerViewItemCountAssertion(1));
    }
}