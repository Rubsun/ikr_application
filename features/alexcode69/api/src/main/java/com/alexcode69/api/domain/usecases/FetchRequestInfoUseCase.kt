package com.alexcode69.api.domain.usecases

interface FetchRequestInfoUseCase {
    suspend fun execute(): RequestInfo
}

data class RequestInfo(
    val url: String,
    val origin: String
)

