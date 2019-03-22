package com.black0hat.customutils.remote

import android.content.Context
import android.text.TextUtils
import android.util.Log
import com.android.volley.*
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.black0hat.customutils.utils.CommonUtils
import org.json.JSONObject
import java.util.*

class Connector {
    private var mRequestQueue: RequestQueue? = null

    private fun getRequestQueue(): RequestQueue? {
        if (mRequestQueue == null) {
            try {
                mRequestQueue = Volley.newRequestQueue(context)
            } catch (ignored: Exception) {
            }

        }

        return mRequestQueue
    }

    /**
     * Start RESTful
     */
    fun postString(
        url: String,
        params: Map<String, String>,
        headers: Map<String, String>,
        responseListener: Response.Listener<String>,
        errorListener: Response.ErrorListener
    ) {
        var parameters = params
//        val context = App.instance
        if (CommonUtils.isNetworkConnected(context)) {
            parameters = removeNulls(parameters)
            printParams(parameters)
            val jsonObjReq = object :
                CustomStringRequest(Request.Method.POST, url, parameters, headers, responseListener, errorListener) {

            }

            addToRequestQueue(jsonObjReq, url)
        } else
            errorListener.onErrorResponse(noInternetVolleyError())
    }

    fun getString(
        url: String,
        params: Map<String, String>,
        headers: Map<String, String>,
        responseListener: Response.Listener<String>,
        errorListener: Response.ErrorListener
    ) {
        if (CommonUtils.isNetworkConnected(context)) {
            val jsonObjReq = object :
                CustomStringRequest(Request.Method.GET, url, HashMap(), headers, responseListener, errorListener) {
            }
            addToRequestQueue(jsonObjReq, url)
        } else
            errorListener.onErrorResponse(noInternetVolleyError())
    }

    fun postJSONObject(
        url: String,
        jsonObject: JSONObject,
        headers: Map<String, String>,
        responseListener: Response.Listener<JSONObject>,
        errorListener: Response.ErrorListener
    ) {
        if (CommonUtils.isNetworkConnected(context)) {
            val jsonObjReq =
                object : JsonObjectRequest(Request.Method.POST, url, jsonObject, responseListener, errorListener) {
                    @Throws(AuthFailureError::class)
                    override fun getHeaders(): Map<String, String> {
                        return headers
                    }
                }
            addToRequestQueue(jsonObjReq, url)
        } else
            errorListener.onErrorResponse(noInternetVolleyError())
    }

    fun getJSONObject(
        url: String,
        headers: Map<String, String>,
        responseListener: Response.Listener<JSONObject>,
        errorListener: Response.ErrorListener
    ) {
        if (CommonUtils.isNetworkConnected(context)) {
            val jsonObjReq = object : JsonObjectRequest(Request.Method.GET, url, responseListener, errorListener) {
                @Throws(AuthFailureError::class)
                override fun getHeaders(): Map<String, String> {
                    return headers
                }
            }
            addToRequestQueue(jsonObjReq, url)
        } else
            errorListener.onErrorResponse(noInternetVolleyError())
    }

    private fun noInternetVolleyError(): VolleyError? {
        //    NetworkResponse response=new NetworkResponse();
        //        VolleyError volleyError = new VolleyError();
        return null
    }

    private fun printParams(params: Map<String, String>) {
        for ((key, value) in params) {
            Log.d("", "$key:$value")
        }
    }

    /**
     * End RESTful
     */
    private fun <T> addToRequestQueue(req: Request<T>, tag: String) {
        // set the default tag if tag is empty
        try {
            req.tag = if (TextUtils.isEmpty(tag)) TAG else tag
            req.setShouldCache(false)
            getRequestQueue()!!.add(req)
        } catch (ignored: Exception) {
        }

    }

//    fun <T> addToRequestQueue(context: Context, req: Request<T>) {
//        req.tag = TAG
//        getRequestQueue(context)!!.add(req)
//    }

    fun cancelPendingRequests(tag: Any) {
        if (mRequestQueue != null) {
            mRequestQueue!!.cancelAll(tag)
        }
    }

    companion object {
        private val TAG = Connector::class.java.simpleName
        private var mInstance: Connector? = null
        private lateinit var context: Context
        val instance: Connector
            @Synchronized get() {
                if (mInstance == null) {
                    mInstance = Connector()
                }
                return mInstance as Connector
            }

        fun initConfig(context: Context) {
            this.context = context
        }

        private fun removeNulls(params: Map<String, String>): Map<String, String> {
            val newParams = HashMap<String, String>()
            for ((key, value) in params) {
                if (!value.equals("null", ignoreCase = true)) newParams[key] = value
            }
            return newParams
        }
    }
}

