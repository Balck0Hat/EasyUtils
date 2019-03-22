package com.black0hat.customutils.remote

import com.android.volley.Response
import org.json.JSONException
import kotlin.collections.HashMap

object Operator {

    fun getString(
        url: String, params: HashMap<String, String> = HashMap(), headers: HashMap<String, String> = HashMap(),
        onSuccess: (response: String?) -> Unit, onError: (response: String?) -> Unit
    ) {
        val responseListener = Response.Listener<String> { response ->
            try {
                onSuccess(response)
            } catch (e: JSONException) {
                onError(e.toString())
            }
        }
        val errorListener = Response.ErrorListener { error ->
            onError(error.message)
        }
        Connector.instance.getString(url, params, headers, responseListener, errorListener)
    }


}