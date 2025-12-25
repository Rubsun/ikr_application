package com.michaelnoskov.impl.domain.usecase

import com.michaelnoskov.api.domain.model.ChartData
import com.michaelnoskov.api.domain.repository.ColorSquareRepository

internal class GetChartDataUseCase(
    private val repository: ColorSquareRepository
) {
    suspend operator fun invoke(): Result<List<ChartData>> = repository.getChartData()
}

