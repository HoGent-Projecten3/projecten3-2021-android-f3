package com.example.faith.utilities

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.NoMatchingViewException
import androidx.test.espresso.ViewAssertion
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import com.example.faith.data.Medium
import org.hamcrest.CoreMatchers.`is`
import java.util.Date

val testMedia = arrayListOf(

    Medium(1, "naammedium", "leukefoto", "www.foto.be", 1, Date()),
    Medium(2, "naammedium", "leukefoto", "www.foto.be", 1, Date()),
    Medium(3, "naammedium", "leukefoto", "www.foto.be", 1, Date())
)

val testMedium = testMedia[0]

class RecyclerViewItemCountAssertion(private val expectedCount: Int) : ViewAssertion {
    override fun check(view: View, noViewFoundException: NoMatchingViewException?) {
        if (noViewFoundException != null) {
            throw noViewFoundException
        }
        val recyclerView = view as RecyclerView
        val adapter = recyclerView.adapter
        assertThat(adapter!!.itemCount, `is`(expectedCount))
    }
}
