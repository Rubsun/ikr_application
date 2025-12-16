package com.example.ikr_application.michaelnoskov.domain

import com.example.ikr_application.michaelnoskov.data.ColorSquareRepository
import kotlinx.coroutines.flow.flow

class UpdateSquareUseCase(
    private val repository: ColorSquareRepository
) {
    suspend operator fun invoke(color: Int? = null, rotation: Float? = null, size: Int? = null) {
        color?.let { repository.updateSquareColor(it) }
        rotation?.let { repository.updateSquareRotation(it) }
        size?.let { repository.updateSquareSize(it) }
    }
}