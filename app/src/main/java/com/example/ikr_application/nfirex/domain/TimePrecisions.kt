package com.example.ikr_application.nfirex.domain

import kotlin.time.Duration
import kotlin.time.Duration.Companion.hours
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.minutes
import kotlin.time.Duration.Companion.seconds

@Deprecated("Use your own classes:)")
enum class TimePrecisions(
    val divider: Duration,
    val typeName: String,
) {
    MS(1.milliseconds, "ms"),
    S(1.seconds, "s"),
    M(1.minutes, "m"),
    H(1.hours, "h");

    init {
        throw IllegalStateException("Removed class")
    }
}