package com.alexcode69.impl.data

import kotlinx.serialization.Serializable
import retrofit2.http.GET

internal interface Alexcode69ApiService {
    @GET("get")
    suspend fun getRequestInfo(): RequestInfoResponseDto
}

@Serializable
internal data class RequestInfoResponseDto(
    val url: String,
    val origin: String
)

internal data class RequestInfoDto(
    val url: String,
    val origin: String
)

