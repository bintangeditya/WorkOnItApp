package id.co.woiapp.utils

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences

object SharedPref {
    private lateinit var sharedPreferences: SharedPreferences

    private const val LOGIN = "LOGIN"
    const val TOKEN = "TOKEN"
    const val NAME = "NAME"
    const val PHOTO = "PHOTO"
    const val IDUSER = "IDUSER"
    const val EMAIL = "EMAIL"

    private fun makeSharedPref(activity: Activity) {
        sharedPreferences = activity.getPreferences(Context.MODE_PRIVATE)
    }

    fun setLogin(status: Boolean, activity: Activity) {
        makeSharedPref(activity)
        val editor = sharedPreferences.edit()
        editor.putBoolean(LOGIN, status)
        editor.apply()
    }

    fun isLogin(activity: Activity): Boolean {
        makeSharedPref(activity)
        return sharedPreferences.getBoolean(LOGIN, false)
    }

    fun saveStringVal(key: String, str: String,activity: Activity) {
        makeSharedPref(activity)
        val editor = sharedPreferences.edit()
        editor.putString(key, str)
        editor.apply()
    }

    fun getStringVal(key: String, activity: Activity): String {
        makeSharedPref(activity)
        return sharedPreferences.getString(key, "") ?: ""
    }

    fun saveIntVal(key: String, str: Int,activity: Activity) {
        makeSharedPref(activity)
        val editor = sharedPreferences.edit()
        editor.putInt(key, str)
        editor.apply()
    }

    fun getIntVal(key: String, activity: Activity): Int {
        makeSharedPref(activity)
        return sharedPreferences.getInt(key, 0)
    }

    fun logout(activity : Activity){
        makeSharedPref(activity)
        val editor = sharedPreferences.edit()
        editor.putBoolean(LOGIN, false)
        editor.apply()

        editor.putInt(IDUSER, 0)
        editor.apply()

        editor.putString(EMAIL, "")
        editor.apply()

        editor.putString(NAME, "")
        editor.apply()
    }

}