package com.example.faith.adapters

import android.view.View
import androidx.databinding.BindingAdapter

/**
 * @author Remi Mestdagh
 */
@BindingAdapter("isGone")
fun bindIsGone(view: View, isGone: Boolean) {
    view.visibility = if (isGone) {
        View.GONE
    } else {
        View.VISIBLE
    }
}
