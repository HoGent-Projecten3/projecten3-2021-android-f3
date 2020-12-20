package com.example.faith.adapters

import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter
import java.text.SimpleDateFormat
import java.util.*

@BindingAdapter("isGone")
fun bindIsGone(view: View, isGone: Boolean) {
    view.visibility = if (isGone) {
        View.GONE
    } else {
        View.VISIBLE
    }
}

@BindingAdapter("isGoneString")
fun bindIsGoneString(view: View, input: String?) {
    view.visibility = if (input.isNullOrEmpty()) {
        View.GONE
    } else {
        View.VISIBLE
    }
}


