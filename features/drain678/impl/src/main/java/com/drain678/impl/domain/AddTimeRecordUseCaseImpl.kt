package com.drain678.impl.domain

import com.drain678.api.domain.usecases.AddTimeRecordUseCase
import com.drain678.impl.data.Drain678Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

internal class AddTimeRecordUseCaseImpl(
    private val repository: Drain678Repository
) : AddTimeRecordUseCase {
    override suspend fun invoke(): Result<Unit> = withContext(Dispatchers.IO) {
        runCatching {
            val info = repository.drain678Info()
            repository.addTimeRecord(info)
        }
    }
}

