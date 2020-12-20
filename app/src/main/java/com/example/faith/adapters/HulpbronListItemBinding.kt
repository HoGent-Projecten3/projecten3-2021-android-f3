package com.example.faith.adapters

import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.example.faith.R
import java.text.SimpleDateFormat
import java.util.*

@BindingAdapter("hulpbronItemColor")
fun bindHulpbronItemColor(cardView: CardView, auteurType: String) {
    val context = cardView.context
    val colorPrivate = ContextCompat.getColor(context, R.color.hotel_lucht_blauw)
    val colorPublic = ContextCompat.getColor(context, R.color.white)
    if (auteurType == "Client") {
        cardView.setCardBackgroundColor(colorPrivate)
    } else {
        cardView.setCardBackgroundColor(colorPublic)
    }
}

@BindingAdapter("bindServerDate")
fun bindServerDate(textView: TextView, date: Date) {
    val locale = Locale("nl", "NL")
    val outputPattern = "dd MMM yyyy"
    val outputDateFormat = SimpleDateFormat(outputPattern, locale)
    textView.text = outputDateFormat.format(date)
}
