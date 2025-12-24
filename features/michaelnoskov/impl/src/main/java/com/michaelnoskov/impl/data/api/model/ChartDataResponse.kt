package com.michaelnoskov.impl.data.api.model

internal data class ChartDataResponse(
    val data: List<ChartDataDto>
)

internal data class ChartDataDto(
    val label: String,
    val value: Float,
    val colorHex: String
)

