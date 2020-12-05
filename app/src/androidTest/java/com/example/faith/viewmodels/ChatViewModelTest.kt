package com.example.faith.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import com.example.faith.data.AppDatabase
import com.example.faith.data.BerichtRepository
import com.example.faith.data.GebruikerRepository
import com.example.faith.data.MediumRepository
import com.example.faith.utilities.testMedium
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.rules.RuleChain
import javax.inject.Inject

@HiltAndroidTest
class ChatViewModelTest {
    private lateinit var appDatabase: AppDatabase
    private lateinit var viewModel: ChatViewModel
    private val hiltRule = HiltAndroidRule(this)
    private val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val rule = RuleChain
            .outerRule(hiltRule)
            .around(instantTaskExecutorRule)

    @Inject
    lateinit var gebruikerRepository: GebruikerRepository
    @Inject
    lateinit var berichtRepository: BerichtRepository

    @Before
    fun setUp() {
        hiltRule.inject()

        val context = InstrumentationRegistry.getInstrumentation().targetContext
        appDatabase = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()

        viewModel = ChatViewModel(gebruikerRepository, berichtRepository)
    }

    @After
    fun tearDown() {
        appDatabase.close()
    }
}