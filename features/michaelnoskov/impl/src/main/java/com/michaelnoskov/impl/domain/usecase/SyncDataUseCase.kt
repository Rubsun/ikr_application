package com.michaelnoskov.impl.domain.usecase

import com.michaelnoskov.api.domain.repository.ColorSquareRepository

internal class SyncDataUseCase(
    private val repository: ColorSquareRepository
) {
    suspend operator fun invoke(): Result<Unit> = repository.syncData()
}

