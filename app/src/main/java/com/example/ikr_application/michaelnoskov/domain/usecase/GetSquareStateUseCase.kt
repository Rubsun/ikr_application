package com.example.ikr_application.michaelnoskov.domain.usecase

import com.example.ikr_application.michaelnoskov.domain.model.SquareData
import com.example.ikr_application.michaelnoskov.domain.repository.ColorSquareRepository
import kotlinx.coroutines.flow.Flow

class GetSquareStateUseCase(
    private val repository: ColorSquareRepository
) {
    operator fun invoke(): Flow<SquareData> = repository.getSquareState()
}