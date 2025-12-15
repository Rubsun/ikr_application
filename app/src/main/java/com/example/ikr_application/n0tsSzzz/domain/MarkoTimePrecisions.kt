package com.example.ikr_application.n0tsSzzz.domain

import kotlin.time.Duration
import kotlin.time.Duration.Companion.hours
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.minutes
import kotlin.time.Duration.Companion.seconds

enum class MarkoTimePrecisions(
    val divider: Duration,
    val typeName: String,
) {
    MS(1.milliseconds, "ms"),
    S(1.seconds, "s"),
    M(1.minutes, "m"),
    H(1.hours, "h"),
}
