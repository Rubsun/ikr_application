package com.antohaot.api.domain.usecases

interface AddTimeRecordUseCase {
    suspend fun invoke(): Result<Unit>
}

