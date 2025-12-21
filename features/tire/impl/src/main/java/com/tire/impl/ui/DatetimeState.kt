package com.tire.impl.ui

import com.tire.api.domain.models.DeviceInfo
import com.tire.api.domain.TimePrecisions

internal data class DatetimeState(
    val dateText: String = "",
    val elapsedText: String = "",
    val precisions: List<TimePrecisions> = TimePrecisions.entries,
    val selectedPrecision: TimePrecisions = TimePrecisions.MS,
    val filterQuery: String = "",
    val devices: List<DeviceInfo> = emptyList(),
    val isLoading: Boolean = false
)
