package com.black0hat.customutils.utils

import android.content.Context
import android.widget.ImageView
import com.black0hat.customutils.AppConstants

object ImageUtils {

    fun getImageFromUrl(context: Context, imageView: ImageView, url: String, placeHolder: Int = 0, error: Int = 0) {
        GlideApp.with(context).load(url)
            .diskCacheStrategy(AppConstants.GlideSettings.diskCacheStrategy)
            .skipMemoryCache(AppConstants.GlideSettings.skipMemoryCache)
            .placeholder(placeHolder)
            .error(error)
            .into(imageView)
    }

}