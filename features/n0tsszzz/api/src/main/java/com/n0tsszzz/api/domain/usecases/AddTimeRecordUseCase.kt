package com.n0tsszzz.api.domain.usecases

interface AddTimeRecordUseCase {
    suspend fun invoke(): Result<Unit>
}

