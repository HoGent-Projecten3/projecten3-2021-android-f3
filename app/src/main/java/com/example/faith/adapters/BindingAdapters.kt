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
fun bindServerDate(textView: TextView, date: String) {
    val locale = Locale("nl", "NL")

    var inputPattern = "yyyy-MM-dd'T'HH:mm:ss"
    val inputDateFormat = SimpleDateFormat(inputPattern, locale)

    val inputDate = inputDateFormat.parse(date)

    val outputPattern = "dd MMM yyyy"
    val outputDateFormat = SimpleDateFormat(outputPattern, locale)

    textView.text = outputDateFormat.format(inputDate)
}
