package com.example.faith

import com.example.faith.data.Hulpbron
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.util.Date

class HulpbronTest {
    private lateinit var hulpbron: Hulpbron

    @Before
    fun setup() {
        hulpbron = Hulpbron(1, "TESTTITEL", "TESTINHOUD", "WWW.TEST.BE", "123", "TEST@TEST.COM", "WWW.TEST.BE", Date(),"Admin")
    }

    @Test
    fun testConstructor() {
        assertEquals(hulpbron.titel,"TESTTITEL")
        assertEquals(hulpbron.inhoud,"TESTINHOUD")
        assertEquals(hulpbron.url,"WWW.TEST.BE")
        assertEquals(hulpbron.telefoonnummer,"123")
        assertEquals(hulpbron.emailadres,"TEST@TEST.COM")
        assertEquals(hulpbron.url,"WWW.TEST.BE")
        assertEquals(hulpbron.auteurType,"Admin")
    }
    @Test
    fun testToString() {
        assertEquals(hulpbron.toString(),"TESTTITEL")
    }
}