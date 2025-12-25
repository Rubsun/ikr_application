package com.argun.network.api

import com.argun.network.api.models.TimeDto

interface TimeApiClient {
    suspend fun getCurrentTime(timezone: String = "UTC"): TimeDto
}

