package com.black0hat.customutils.utils

import android.app.Dialog
import android.content.Context

object DialogUtils {

    private fun dialogSetting(context: Context, res: Int): Dialog {
        val dialog = Dialog(context)
        dialog.window!!.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.setContentView(res)

        dialog.show()
        return dialog
    }

    fun dialog(context: Context, layoutRes: Int, styleRes: Int = 0, onClick: (Dialog) -> Unit) {
        val dialog = dialogSetting(context, layoutRes)
        if (styleRes != 0)
            dialog.window!!.attributes.windowAnimations = styleRes
        onClick(dialog)
    }

}