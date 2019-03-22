package com.black0hat.customutils

import android.content.Context
import android.widget.Toast
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONException


object MyUtils {

    fun plus(a: Int, b: Int): Int {
        print(::minus)
        return a + b
    }

    fun minus(a: Int, b: Int): Int {
        return a - b
    }

    fun checkEmail(email: String, result: CheckInterface) {
        if (email != "") {
            result.execute(true)
        } else {
            result.execute(false)
        }
    }

    fun volleyRequest(
        context: Context,
        id: String,
        roomName: String,
        onComplete: (accessToken: String, roomName: String) -> Unit
    ) {
        val queue = Volley.newRequestQueue(context)
        val url = "https://fandango-lizard-8074.twil.io/emoje-token?identity=$$id&type=peer-to-peer&room=$roomName"
        val request = JsonObjectRequest(url, null,
            Response.Listener { response ->
                try {
                    val accessToken: String = response.getString("token")
                    onComplete(accessToken, roomName)
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            },
            Response.ErrorListener { error ->
                Toast.makeText(context, error.toString(), Toast.LENGTH_LONG).show()
            }
        )
        queue.add(request)
    }


}