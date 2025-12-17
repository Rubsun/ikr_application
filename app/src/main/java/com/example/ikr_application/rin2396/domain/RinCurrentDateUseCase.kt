package com.example.ikr_application.rin2396.domain

import com.example.ikr_application.rin2396.data.RinRepository
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat

class RinCurrentDateUseCase() {
    private val dateTimeFormatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")

    suspend fun date(): String {
        val timestamp = RinRepository.INSTANCE.rinInfo().currentTime
        val dateTime = DateTime(timestamp)
        return dateTimeFormatter.print(dateTime)
    }
}