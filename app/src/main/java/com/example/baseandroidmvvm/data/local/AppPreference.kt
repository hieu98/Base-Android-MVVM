package com.example.baseandroidmvvm.data.local

import android.content.Context
import com.example.baseandroidmvvm.Constants.PRIVATE_DATA_NAME

class AppPreference(context: Context) {
    private val sharePrefs = context.getSharedPreferences(PRIVATE_DATA_NAME, Context.MODE_PRIVATE)

    private fun getEditor() = sharePrefs.edit()

    fun getString(key: String, defaultValue: String = ""): String {
        return sharePrefs.getString(key, defaultValue).orEmpty()
    }

    fun saveString(key: String, value: String) {
        return getEditor().putString(key, value).apply()
    }

    fun getBoolean(key: String, defaultValue: Boolean): Boolean {
        return sharePrefs.getBoolean(key, defaultValue)
    }

    fun saveBoolean(key: String, value: Boolean) {
        return getEditor().putBoolean(key, value).apply()
    }

    fun getInt(key: String): Int {
        return sharePrefs.getInt(key, 0)
    }

    fun saveInt(key: String, value: Int) {
        return getEditor().putInt(key, value).apply()
    }

    fun getLong(key: String): Long {
        return sharePrefs.getLong(key, 0)
    }

    fun saveLong(key: String, value: Long) {
        return getEditor().putLong(key, value).apply()
    }
}