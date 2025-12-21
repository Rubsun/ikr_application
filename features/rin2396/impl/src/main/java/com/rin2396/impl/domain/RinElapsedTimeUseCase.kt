package com.rin2396.impl.domain

import com.rin2396.impl.data.RinRepository

internal class RinElapsedTimeUseCase {
    fun value(precision: RinTimePrecisions): Long {
        val nano = System.nanoTime()
        return when (precision) {
            RinTimePrecisions.S -> nano / 1_000_000_000
            RinTimePrecisions.MS -> nano / 1_000_000
            RinTimePrecisions.NS -> nano
        }
    }
}
