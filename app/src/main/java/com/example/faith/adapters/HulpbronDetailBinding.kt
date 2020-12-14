package com.example.faith.adapters

import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.example.faith.R

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
