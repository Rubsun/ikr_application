package com.drain678.api.domain.usecases

import kotlinx.coroutines.flow.Flow

interface AddTimeRecordUseCase {
    suspend fun invoke(): Result<Unit>
}

