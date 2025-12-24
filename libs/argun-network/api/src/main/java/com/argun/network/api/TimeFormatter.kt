package com.argun.network.api

interface TimeFormatter {
    fun formatDateTime(timestamp: Long): String
    fun formatElapsedTime(elapsedMillis: Long): String
}

