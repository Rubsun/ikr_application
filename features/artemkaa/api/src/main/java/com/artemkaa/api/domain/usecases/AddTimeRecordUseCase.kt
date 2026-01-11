package com.artemkaa.api.domain.usecases

interface AddTimeRecordUseCase {
    suspend fun invoke(): Result<Unit>
}

