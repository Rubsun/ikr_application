package com.drain678.api.domain.usecases

interface AddTimeRecordUseCase {
    suspend fun invoke(): Result<Unit>
}

