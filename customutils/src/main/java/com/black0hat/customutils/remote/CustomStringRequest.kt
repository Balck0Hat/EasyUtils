package com.black0hat.customutils.remote

import com.android.volley.*
import com.android.volley.toolbox.HttpHeaderParser
import java.io.UnsupportedEncodingException

open class CustomStringRequest(method: Int, url: String, private val params: Map<String, String>, private val headers: Map<String, String>, private val listener: Response.Listener<String>, errorListener: Response.ErrorListener) : Request<String>(method, url, errorListener) {

    init {
        this.retryPolicy = DefaultRetryPolicy(TIMEOUT_MS, MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)
    }

    @Throws(AuthFailureError::class)
    override fun getParams(): Map<String, String> {
        return params
    }

    override fun getBodyContentType(): String {
        return super.getBodyContentType()
    }


    @Throws(AuthFailureError::class)
    override fun getHeaders(): Map<String, String> {
        return if (headers.isNotEmpty()) headers else super.getHeaders()
    }

    override fun parseNetworkError(volleyError: VolleyError): VolleyError {
        try {
            val s = String(volleyError.networkResponse.data, charset(HttpHeaderParser.parseCharset(volleyError.networkResponse.headers, "utf-8")))
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return super.parseNetworkError(volleyError)
    }

    override fun parseNetworkResponse(response: NetworkResponse): Response<String> {
        try {
            val jsonString = String(response.data, charset(HttpHeaderParser.parseCharset(response.headers)))
            return Response.success(jsonString, HttpHeaderParser.parseCacheHeaders(response))
        } catch (e: UnsupportedEncodingException) {
            return Response.error(ParseError(e))
        } catch (je: Exception) {
            return Response.error(ParseError(je))
        }

    }

    override fun deliverResponse(response: String) {
        listener.onResponse(response)
    }

    companion object {
        private val TIMEOUT_MS = 10000
        private val MAX_RETRIES = 2
    }
}


