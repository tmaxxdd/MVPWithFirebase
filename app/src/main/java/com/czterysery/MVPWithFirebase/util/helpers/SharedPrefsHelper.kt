package com.czterysery.MVPWithFirebase.util.helpers

import android.content.Context
import android.content.SharedPreferences
import org.jetbrains.anko.defaultSharedPreferences

/*
    This class manages SharedPreferences in the app.
    This is modified example from:
    https://stackoverflow.com/a/53240443
 */
class SharedPrefsHelper(context: Context) {

    private var prefs: SharedPreferences = context.defaultSharedPreferences

    fun read(key: String, value: String): String? {
        return prefs.getString(key, value)
    }

    fun write(key: String, value: String) {
        val prefsEditor: SharedPreferences.Editor = prefs.edit()
        with(prefsEditor) {
            putString(key, value)
            apply()
        }
    }

}