package com.antohaot.api.domain.usecases

import kotlinx.coroutines.flow.Flow

interface AddTimeRecordUseCase {
    suspend fun invoke(): Result<Unit>
}

