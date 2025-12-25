package com.n0tsszzz.network.data

import android.util.Log
import com.n0tsszzz.network.api.TimeApiClient
import com.n0tsszzz.network.api.models.TimeDto
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone

private const val TAG = "N0tsszzzNetwork"

/**
 * Реализация TimeApiClient через Retrofit.
 * Инкапсулирует всю логику работы с Retrofit и сетью.
 */
internal class RetrofitTimeApiClient(
    private val service: TimeService
) : TimeApiClient {
    override suspend fun getCurrentTime(): TimeDto {
        Log.d(TAG, "RetrofitTimeApiClient.getCurrentTime() called")
        return try {
            val response = service.getCurrentTime()
            Log.d(TAG, "API response received: ${response.currentDateTime}")
            // Преобразуем currentDateTime в unix timestamp
            // HTTP World Clock API возвращает формат ISO 8601, например: "2025-12-24T15:08Z" (без секунд)
            // Используем SimpleDateFormat для совместимости с API 24+
            var dateTimeString = response.currentDateTime
            // Если формат без секунд (например "2025-12-24T15:08Z"), добавляем ":00" перед "Z"
            if (dateTimeString.matches(Regex("\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}Z"))) {
                dateTimeString = dateTimeString.replace("Z", ":00Z")
            }
            
            // Парсим ISO 8601 формат с помощью SimpleDateFormat
            val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US).apply {
                timeZone = TimeZone.getTimeZone("UTC")
            }
            val date = dateFormat.parse(dateTimeString)
            val unixtime = date?.time?.div(1000) ?: throw IllegalArgumentException("Failed to parse date: $dateTimeString")
            
            TimeDto(
                datetime = response.currentDateTime,
                unixtime = unixtime
            )
        } catch (e: Exception) {
            Log.e(TAG, "Error getting current time from API", e)
            throw e
        }
    }
}

