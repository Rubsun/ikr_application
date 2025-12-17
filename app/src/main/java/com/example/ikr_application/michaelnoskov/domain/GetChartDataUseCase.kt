package com.example.ikr_application.michaelnoskov.domain

import com.example.ikr_application.michaelnoskov.data.ColorSquareRepository

class GetChartDataUseCase(
    private val repository: ColorSquareRepository
) {
    operator fun invoke(): List<Pair<String, Float>> {
        return repository.getChartData()
    }
}