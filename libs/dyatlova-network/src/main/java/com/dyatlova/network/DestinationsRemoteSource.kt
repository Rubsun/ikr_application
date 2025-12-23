package com.dyatlova.network

/**
 * Public entry point for loading remote destinations.
 * All Retrofit/OkHttp specifics are hidden inside this module.
 */
interface DestinationsRemoteSource {
    suspend fun loadDestinations(limit: Int = DEFAULT_LIMIT): List<RemoteDestination>

    companion object {
        const val DEFAULT_LIMIT = 12
    }
}

data class RemoteDestination(
    val id: String,
    val title: String,
    val country: String,
    val imageUrl: String,
    val tags: List<String>,
)


