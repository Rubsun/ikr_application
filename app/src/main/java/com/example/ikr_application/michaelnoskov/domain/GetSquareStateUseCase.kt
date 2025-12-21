package com.example.ikr_application.michaelnoskov.domain

import com.example.ikr_application.michaelnoskov.data.ColorSquareRepository
import com.example.ikr_application.michaelnoskov.data.models.SquareState
import kotlinx.coroutines.flow.Flow

class GetSquareStateUseCase(
    private val repository: ColorSquareRepository
) {
    operator fun invoke(): Flow<SquareState> {
        return repository.squareState
    }
}