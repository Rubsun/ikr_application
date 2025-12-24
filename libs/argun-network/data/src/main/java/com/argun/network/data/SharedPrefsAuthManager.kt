package com.argun.network.data

import android.content.Context
import android.content.SharedPreferences
import com.argun.network.api.AuthManager

internal class SharedPrefsAuthManager(
    private val context: Context
) : AuthManager {
    
    private val prefs: SharedPreferences = context.getSharedPreferences(
        "argun_auth_prefs",
        Context.MODE_PRIVATE
    )
    
    private val tokenKey = "auth_token"
    
    override fun saveToken(token: String) {
        prefs.edit().putString(tokenKey, token).apply()
    }
    
    override fun getToken(): String? {
        return prefs.getString(tokenKey, null)
    }
    
    override fun clearToken() {
        prefs.edit().remove(tokenKey).apply()
    }
    
    override fun isAuthorized(): Boolean {
        return getToken() != null
    }
}

