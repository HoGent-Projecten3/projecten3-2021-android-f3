package com.example.faith.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.faith.utilities.getValue
import kotlinx.coroutines.runBlocking
import org.hamcrest.Matchers.equalTo
import org.junit.After
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MediumDaoTest {
    private lateinit var database: AppDatabase
    private lateinit var mediumDao: MediumDao
    private val medium1 = Medium(1, "naammedium", "leukefoto", "www.foto.be")
    private val medium2 = Medium(2, "naammedium2", "leukefoto2", "www.foto2.be")
    private val medium3 = Medium(3, "naammedium3", "leukefoto3", "www.foto3.be")

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun createDb() = runBlocking {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        database = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        mediumDao = database.mediumDao()

        // Insert plants in non-alphabetical order to test that results are sorted by name
        mediumDao.insertAll(listOf(medium1, medium2, medium3))
    }

    @After
    fun closeDb() {
        database.close()
    }

    @Test
    fun testGetMedia() {
        val mediaList = getValue(mediumDao.getMedia())
        assertThat(mediaList.size, equalTo(3))
    }

    @Test
    fun testGetMedium() {
        assertThat(getValue(mediumDao.getMedium(medium1.mediumId)), equalTo(medium1))
    }

    @Test
    fun testDeleteMedium() = runBlocking {
        val mediaList = getValue(mediumDao.getMedia())
        mediumDao.deleteMedium(medium1)
        assertThat(getValue(mediumDao.getMedia()).size, equalTo(2))

    }

    @Test
    fun testInsertOne() = runBlocking {
        val medium = Medium(4, "naam", "beschrijving", "url")
        mediumDao.insertOne(medium)
        assertThat(getValue(mediumDao.getMedia()).size, equalTo(4))
    }
}