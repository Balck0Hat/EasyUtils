package com.black0hat.customutils.utils

import android.content.Context
import android.content.Intent

object ShareUtils {
    fun share(context: Context, text: String = "") {
        val sharingIntent = Intent(Intent.ACTION_SEND)
        sharingIntent.type = "text/plain"
        sharingIntent.putExtra(Intent.EXTRA_TEXT, text)
        context.startActivity(Intent.createChooser(sharingIntent, "Send a message via:"))
    }
}