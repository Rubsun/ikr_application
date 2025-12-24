package com.michaelnoskov.impl.domain.usecase

import com.michaelnoskov.api.domain.model.SquareData
import com.michaelnoskov.api.domain.repository.ColorSquareRepository
import kotlinx.coroutines.flow.Flow

internal class GetSquareStateUseCase(
    private val repository: ColorSquareRepository
) {
    operator fun invoke(): Flow<SquareData> = repository.getSquareState()
}

