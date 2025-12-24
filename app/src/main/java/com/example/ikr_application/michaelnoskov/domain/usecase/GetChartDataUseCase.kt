package com.example.ikr_application.michaelnoskov.domain.usecase

import com.example.ikr_application.michaelnoskov.domain.model.ChartData
import com.example.ikr_application.michaelnoskov.domain.repository.ColorSquareRepository

class GetChartDataUseCase(
    private val repository: ColorSquareRepository
) {
    suspend operator fun invoke(): Result<List<ChartData>> = repository.getChartData()
}