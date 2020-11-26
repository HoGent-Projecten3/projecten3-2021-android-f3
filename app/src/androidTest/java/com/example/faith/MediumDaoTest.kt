package com.example.faith

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.faith.data.AppDatabase
import com.example.faith.data.Medium
import com.example.faith.data.MediumDao
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MediumDaoTest {
    private lateinit var database: AppDatabase
    private lateinit var mediumDao: MediumDao
    private val medium1 = Medium(1,"naammedium","leukefoto","www.foto.be")
    private val medium2 = Medium(2,"naammedium2","leukefoto2","www.foto2.be")
    private val medium3 = Medium(3,"naammedium3","leukefoto3","www.foto3.be")
}