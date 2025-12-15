package com.example.ikr_application.tire.ui

import com.example.ikr_application.tire.data.models.DeviceInfo
import com.example.ikr_application.tire.domain.TimePrecisions

data class DatetimeState(
    val dateText: String = "",
    val elapsedText: String = "",
    val precisions: List<TimePrecisions> = TimePrecisions.entries,
    val selectedPrecision: TimePrecisions = TimePrecisions.MS,
    val filterQuery: String = "",
    val devices: List<DeviceInfo> = emptyList(),
    val isLoading: Boolean = false
)
