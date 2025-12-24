package com.example.ikr_application.michaelnoskov.data.api.model

data class ChartDataResponse(
    val data: List<ChartDataDto>
)

data class ChartDataDto(
    val label: String,
    val value: Float,
    val colorHex: String
)