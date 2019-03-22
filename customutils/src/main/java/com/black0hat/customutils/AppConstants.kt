package com.black0hat.customutils

import com.bumptech.glide.load.engine.DiskCacheStrategy

object AppConstants {


    object GlideSettings {
        val diskCacheStrategy = DiskCacheStrategy.ALL!!
        const val skipMemoryCache = false
    }

}