package com.example.ikr_application.kristevt.domain

enum class TimePrecisions(
    val divider: Long,
    val typeName: String,
) {
    MS(1, "ms"),
    S(1000, "s"),
    M(60 * 1000, "m"),
    H(60 * 60 * 1000, "h"),
}