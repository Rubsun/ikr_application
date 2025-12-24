package com.example.impl.data

import android.content.Context
import androidx.core.content.edit

internal class F1Preferences(context: Context) {
    private val prefs = context.getSharedPreferences("f1_prefs", Context.MODE_PRIVATE)

    fun saveLastSound(sound: String?) {
        prefs.edit { putString(KEY_LAST_SOUND, sound) }
    }

    fun getLastSound(): String? = prefs.getString(KEY_LAST_SOUND, null)

    fun saveLastCarName(name: String?) {
        prefs.edit { putString(KEY_LAST_CAR_NAME, name) }
    }

    fun getLastCarName(): String? = prefs.getString(KEY_LAST_CAR_NAME, null)

    companion object {
        private const val KEY_LAST_SOUND = "last_sound"
        private const val KEY_LAST_CAR_NAME = "last_car_name"
    }
}
