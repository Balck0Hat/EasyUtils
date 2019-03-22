package com.black0hat.customutils.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.Uri
import android.os.SystemClock
import android.provider.Settings
import android.telephony.TelephonyManager
import android.util.Patterns
import com.google.gson.Gson
import io.michaelrocks.libphonenumber.android.PhoneNumberUtil
import java.text.SimpleDateFormat
import java.util.*
import android.view.inputmethod.InputMethodManager
import com.black0hat.customutils.R


object CommonUtils {
    fun getGson(): Gson {
//        return GsonBuilder().setPrettyPrinting().create()
        return Gson()

    }

    fun isNetworkConnected(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = cm.activeNetworkInfo
        return activeNetwork != null && activeNetwork.isConnected

    }

    fun getRandomName(): String {
        val userName: String = "Emoje-" + Random().nextInt(99999) + 1
        return userName
    }

    fun getRandomFileName(fileName: String): String {
        return "file-" + (Random().nextInt(9999999) + 1) + fileName
    }

    fun getTimestamp(): Long {
        return System.currentTimeMillis() / 1000
    }

    fun getCountryCode(context: Context): String {
        val tm = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        var countryCodeValue = ""
        val networkCountryIso = tm.networkCountryIso
        val simCountryIso = tm.simCountryIso

        countryCodeValue = if (networkCountryIso.isBlank())
            simCountryIso
        else
            networkCountryIso

        return countryCodeValue
    }

    fun getCurrentCountryCode(context: Context): Int {
        val telephonyManager = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        val networkCountryIso = telephonyManager.networkCountryIso
        val simCountryIso = telephonyManager.simCountryIso
        var countryIso = ""
        if (networkCountryIso.isBlank())
            countryIso = simCountryIso.toUpperCase()
        else
            countryIso = networkCountryIso.toUpperCase()

        return PhoneNumberUtil.createInstance(context).getCountryCodeForRegion(countryIso)
    }

    fun getCountryName(code: String): String {
        val loc = Locale("", code)
        return loc.displayCountry
    }

    fun openPlayStoreForApp(context: Context, playStoreUrl: String) {
        try {
            context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(playStoreUrl)))
        } catch (e: android.content.ActivityNotFoundException) {
            context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(playStoreUrl)))
        }

    }

    val timestamp: String get() = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(Date())

    @SuppressLint("all")
    fun getDeviceId(context: Context): String {
        return Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
    }

    private fun isEmailValid(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun signUpCheck(name: String, email: String, password: String, rePassword: String): Boolean =
        isPasswordValid(password, rePassword) && isEmailValid(email) && !name.isEmpty()


    private fun isPasswordValid(password: String, rePassword: String): Boolean =
        password.length >= 6 && !password.isEmpty() && rePassword == password

    fun signInCheck(email: String, password: String): Boolean =
        isEmailValid(email) && isPasswordValid(password, password)

    fun userNameCheck(userName: String): Boolean = isUserNameValid(userName)

    private fun isUserNameValid(userName: String): Boolean = (userName == "" || userName.length > 3)

    fun sendHelpEmail(context: Context, msg: String, myEmail: String) {
        val intent = Intent(Intent.ACTION_SENDTO)
        intent.data = Uri.parse("mailto:$myEmail") // only email apps should handle this
        intent.putExtra(Intent.EXTRA_EMAIL, myEmail)
        intent.putExtra(Intent.EXTRA_SUBJECT, "Feedback email")
        intent.putExtra(Intent.EXTRA_TEXT, msg)
        if (intent.resolveActivity(context.packageManager) != null) {
            context.startActivity(intent)
        }
    }

    fun goToWeb(context: Context, url: String) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(url)
        context.startActivity(intent)

    }

    var mLastClickTime: Long = 0
    private var isDoubleClick = false

    fun onDoubleClick(): Boolean {
        mLastClickTime = 0
        if (SystemClock.elapsedRealtime() - mLastClickTime < 2000) {
            return true
        }

        mLastClickTime = SystemClock.elapsedRealtime()
        return false
    }

    fun closeKeyboard(context: Activity) {
        val inputManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(context.currentFocus!!.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
    }

    fun openKeyboard(context: Context) {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, 0)
    }
}