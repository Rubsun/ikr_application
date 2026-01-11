package com.argun.impl.domain

import com.argun.api.domain.usecases.AddTimeRecordUseCase
import com.argun.impl.data.ArgunRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

internal class AddTimeRecordUseCaseImpl(
    private val repository: ArgunRepository
) : AddTimeRecordUseCase {
    override suspend fun invoke(): Result<Unit> = withContext(Dispatchers.IO) {
        runCatching {
            val info = repository.argunInfo()
            repository.addTimeRecord(info)
        }
    }
}

