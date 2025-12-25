package com.argun.network.data

import com.argun.network.api.TimeFormatter
import org.joda.time.DateTime
import org.joda.time.Duration
import org.joda.time.format.DateTimeFormat

internal class JodaTimeFormatter : TimeFormatter {
    private val dateTimeFormatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")

    override fun formatDateTime(timestamp: Long): String {
        val dateTime = DateTime(timestamp)
        return dateTimeFormatter.print(dateTime)
    }

    override fun formatElapsedTime(elapsedMillis: Long): String {
        val duration = Duration(elapsedMillis)
        val hours = duration.standardHours
        val minutes = duration.standardMinutes % 60
        val seconds = duration.standardSeconds % 60
        val milliseconds = duration.millis % 1000 / 100
        return String.format("%02d:%02d:%02d.%01d", hours, minutes, seconds, milliseconds)
    }
}

