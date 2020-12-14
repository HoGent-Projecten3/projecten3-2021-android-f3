package com.example.faith.data

import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.time.LocalDate
import java.time.ZoneOffset
import java.util.Date

class MediumTest {
    private lateinit var medium: Medium

    @Before
    fun setUp() {
        medium = Medium(
            1,
            "naammedium",
            "leukefoto",
            "www.foto.be",
            1,

            Date.from(LocalDate.of(2020, 5, 5).atStartOfDay().toInstant(ZoneOffset.UTC))
        )
    }

    @Test
    fun test_default_values() {
        val medium2 = Medium(2, "henk", url = "", "", 4, Date())
        assertEquals("", medium2.url)
    }

    @Test
    fun test_toString() {
        assertEquals("naammedium", medium.toString())
    }

    @Test
    fun test_dateString() {
        assertEquals("05/05/2020", medium.toSimpleString())
    }
}
