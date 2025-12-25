package com.argun.impl.domain

import com.argun.api.domain.models.Zadacha
import com.argun.api.domain.usecases.GetZadachiUseCase
import com.argun.impl.data.ArgunRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

internal class GetZadachiUseCaseImpl(
    private val repository: ArgunRepository
) : GetZadachiUseCase {
    override suspend fun invoke(): Result<List<Zadacha>> = withContext(Dispatchers.IO) {
        runCatching {
            repository.getAllZadachi()
        }
    }
}

