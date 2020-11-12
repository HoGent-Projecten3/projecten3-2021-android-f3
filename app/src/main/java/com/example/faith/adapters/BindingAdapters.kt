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

@BindingAdapter("bindServerDate")
fun bindServerDate(textView: TextView, date: Date) {
    val locale = Locale("nl", "NL")
    val pattern = "dd MMM yyyy"
    val dateFormat = SimpleDateFormat(pattern, locale)
    textView.text = dateFormat.format(date)
}
