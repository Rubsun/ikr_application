package com.ikr.libs.network

import kotlinx.serialization.Serializable
import kotlinx.coroutines.flow.Flow

/**
 * Абстракция для работы с API
 */
interface ApiClient {
    suspend fun <T> execute(request: ApiRequest<T>): ApiResponse<T>
}

/**
 * Запрос к API
 */
interface ApiRequest<T> {
    val url: String
    val method: HttpMethod
    val headers: Map<String, String>?
    val body: Any?
}

/**
 * Ответ от API
 */
sealed class ApiResponse<T> {
    data class Success<T>(val data: T) : ApiResponse<T>()
    data class Error<T>(val message: String, val code: Int? = null) : ApiResponse<T>()
}

enum class HttpMethod {
    GET, POST, PUT, DELETE
}

