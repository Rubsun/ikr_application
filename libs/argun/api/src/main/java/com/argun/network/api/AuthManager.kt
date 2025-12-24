package com.argun.network.api

interface AuthManager {
    fun saveToken(token: String)
    fun getToken(): String?
    fun clearToken()
    fun isAuthorized(): Boolean
}

