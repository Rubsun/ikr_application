package com.michaelnoskov.impl.domain.usecase

import com.michaelnoskov.api.domain.repository.ColorSquareRepository

internal class UpdateSquareUseCase(
    private val repository: ColorSquareRepository
) {
    suspend operator fun invoke(
        color: Int? = null,
        rotation: Float? = null,
        size: Int? = null
    ) {
        color?.let { repository.updateSquareColor(it) }
        rotation?.let { repository.updateSquareRotation(it) }
        size?.let { repository.updateSquareSize(it) }
    }
}

