package com.example.faith.adapters

import android.net.Uri
import android.widget.ImageView
import android.widget.VideoView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
/**
 * @author Remi Mestdagh
 */
@BindingAdapter("imageFromUrl")
fun bindImageFromUrl(view: ImageView, imageUrl: String?) {
    if (!imageUrl.isNullOrEmpty()) {
        Glide.with(view.context)
            .load(imageUrl)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(view)
    }
}

@BindingAdapter("videoFromUrl")
fun bindVideoFromUrl(view: VideoView, imageUrl: String?) {
    if (!imageUrl.isNullOrEmpty()) {
        view.setVideoURI(Uri.parse(imageUrl))
    }
}
