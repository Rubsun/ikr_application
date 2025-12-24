package com.argun.network.data

import com.argun.network.api.AuthManager
import okhttp3.Interceptor
import okhttp3.Response

internal class ErrorInterceptor(
    private val authManager: AuthManager
) : Interceptor {
    
    override fun intercept(chain: Interceptor.Chain): Response {
        val response = chain.proceed(chain.request())
        
        if (response.code == 401) {
            authManager.clearToken()
        }
        
        return response
    }
}

