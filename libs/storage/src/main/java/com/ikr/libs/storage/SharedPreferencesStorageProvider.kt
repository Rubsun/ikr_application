package com.ikr.libs.storage

import android.content.Context
import android.content.SharedPreferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Реализация StorageProvider через SharedPreferences
 */
class SharedPreferencesStorageProvider(
    private val context: Context,
    private val preferencesName: String = "app_preferences"
) : StorageProvider {

    private val prefs: SharedPreferences by lazy {
        context.getSharedPreferences(preferencesName, Context.MODE_PRIVATE)
    }

    override suspend fun saveString(key: String, value: String) {
        withContext(Dispatchers.IO) {
            prefs.edit().putString(key, value).apply()
        }
    }

    override suspend fun getString(key: String, defaultValue: String): String {
        return withContext(Dispatchers.IO) {
            prefs.getString(key, defaultValue) ?: defaultValue
        }
    }

    override suspend fun saveInt(key: String, value: Int) {
        withContext(Dispatchers.IO) {
            prefs.edit().putInt(key, value).apply()
        }
    }

    override suspend fun getInt(key: String, defaultValue: Int): Int {
        return withContext(Dispatchers.IO) {
            prefs.getInt(key, defaultValue)
        }
    }

    override suspend fun saveBoolean(key: String, value: Boolean) {
        withContext(Dispatchers.IO) {
            prefs.edit().putBoolean(key, value).apply()
        }
    }

    override suspend fun getBoolean(key: String, defaultValue: Boolean): Boolean {
        return withContext(Dispatchers.IO) {
            prefs.getBoolean(key, defaultValue)
        }
    }

    override suspend fun clear() {
        withContext(Dispatchers.IO) {
            prefs.edit().clear().apply()
        }
    }
}

