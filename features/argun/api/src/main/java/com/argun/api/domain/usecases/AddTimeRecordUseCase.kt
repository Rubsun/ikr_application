package com.argun.api.domain.usecases

interface AddTimeRecordUseCase {
    suspend fun invoke(): Result<Unit>
}

