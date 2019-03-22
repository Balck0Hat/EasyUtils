package com.black0hat.customutils

import android.content.Context
import android.widget.Toast
import android.R.layout
import android.graphics.drawable.Drawable
import android.view.Gravity
import androidx.core.content.ContextCompat.getColor


object myToast {

    fun toast(context: Context, text: String, length: Int) {
        Toast.makeText(context, text, length).show()
    }
//
//    fun toast(
//        context: Context,
//        text: String,
//        color: Int,
//        length: Int
//    ): Toast {
//        val customToast = Toast.makeText(context, text, length)
//        customToast.setGravity(Gravity.CENTER_VERTICAL, 0, 0)
//        customToast.view.setBackgroundColor(color)
//        //        toast.view = layout
//
//
//        customToast.show()
//
//        return customToast
//    }

    class Tost(
        private var context: Context,
        text: String,
        color: Int = getColor(context, R.color.default_toast),
        drawable: Int = 0,
        length: Int = android.widget.Toast.LENGTH_SHORT
    ) {
        private var customToast: Toast = Toast.makeText(context, text, length)

        init {
            customToast.setGravity(Gravity.CENTER_VERTICAL, 0, 0)
            customToast.view.setBackgroundColor(color)
            customToast.show()
            customToast.view.setBackgroundResource(drawable)
        }

        fun background(drawable: Int): Toast {
            customToast.view.setBackgroundResource(drawable)
            return customToast
        }

        fun <Toast> backgroundColor(color: Int) {
//        customToast.view.setBackgroundColor(color)
        }


    }


    fun minus(a: Int, b: Int): Int {
        return a - b
    }

    fun times(a: Int, b: Int): Int {
        return a * b
    }
}