package com.example.faith.data

import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class MediumTest {
    private lateinit var medium: Medium

    @Before
    fun setUp(){
        medium = Medium(1,"naammedium","leukefoto","www.foto.be")
    }

    @Test
    fun test_default_values(){
        val medium2 = Medium(2, "henk","leukefoto")
        assertEquals("",medium2.url)
    }

    @Test fun test_toString() {
        assertEquals("naammedium",medium.toString())
    }
}