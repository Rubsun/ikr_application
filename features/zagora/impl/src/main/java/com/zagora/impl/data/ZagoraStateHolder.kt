package com.zagora.impl.data

import android.content.SharedPreferences

internal class ZagoraStateHolder(private val prefs: SharedPreferences) {

    private val selectedBreedKey = "selected_breed"

    fun saveSelectedBreed(breed: String) {
        prefs.edit().putString(selectedBreedKey, breed).apply()
    }

    fun getSelectedBreed(defaultValue: String): String {
        return prefs.getString(selectedBreedKey, defaultValue) ?: defaultValue
    }
}
