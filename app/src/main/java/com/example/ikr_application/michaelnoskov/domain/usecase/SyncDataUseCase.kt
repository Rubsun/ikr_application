package com.example.ikr_application.michaelnoskov.domain.usecase

import com.example.ikr_application.michaelnoskov.domain.repository.ColorSquareRepository

class SyncDataUseCase(
    private val repository: ColorSquareRepository
) {
    suspend operator fun invoke(): Result<Unit> = repository.syncData()
}