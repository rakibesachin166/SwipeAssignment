package com.sacdev.swipeassignment

import android.annotation.SuppressLint

import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions


class GlideBindingAdapter {
    companion object{
        @SuppressLint("CheckResult")
        @JvmStatic
        @BindingAdapter("imageUrl", requireAll = false)
        fun setImageWithPlaceholder(
            imageView: AppCompatImageView,
            imageUrl: String,
        ) {
            val requestManager = Glide.with(imageView.context).load(imageUrl)
                .apply(RequestOptions().centerCrop())
                .placeholder(R.drawable.baseline_broken_image_24)
                .transition(
                    DrawableTransitionOptions.withCrossFade()
                )
            requestManager.into(imageView)
        }
    }
}