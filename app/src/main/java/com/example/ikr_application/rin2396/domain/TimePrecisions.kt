package com.example.ikr_application.rin2396.domain

import kotlin.time.Duration
import kotlin.time.Duration.Companion.hours
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.minutes
import kotlin.time.Duration.Companion.seconds

enum class TimePrecisions(
    val divider: Duration,
    val typeName: String,
) {
    MS(1.milliseconds, "ms"),
    S(1.seconds, "s"),
    M(1.minutes, "m"),
    H(1.hours, "h"),
}