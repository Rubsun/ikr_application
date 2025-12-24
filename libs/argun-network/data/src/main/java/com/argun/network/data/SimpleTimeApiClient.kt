package com.argun.network.data

import com.argun.network.api.TimeApiClient
import com.argun.network.api.models.TimeDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

internal class SimpleTimeApiClient : TimeApiClient {
    override suspend fun getCurrentTime(timezone: String): TimeDto = withContext(Dispatchers.IO) {
        val currentTime = System.currentTimeMillis()
        val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS", Locale.getDefault())
        dateFormat.timeZone = TimeZone.getTimeZone(timezone)
        val datetime = dateFormat.format(Date(currentTime))
        
        TimeDto(
            datetime = datetime,
            timezone = timezone,
            unixtime = currentTime / 1000
        )
    }
}

