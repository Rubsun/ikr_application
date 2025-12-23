package com.alexcode69.api.domain.usecases

interface AddTimeEntryUseCase {
    suspend fun execute(label: String)
}

