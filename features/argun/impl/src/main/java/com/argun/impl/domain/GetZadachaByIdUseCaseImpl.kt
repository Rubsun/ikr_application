package com.argun.impl.domain

import com.argun.api.domain.models.Zadacha
import com.argun.api.domain.usecases.GetZadachaByIdUseCase
import com.argun.impl.data.ArgunRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

internal class GetZadachaByIdUseCaseImpl(
    private val repository: ArgunRepository
) : GetZadachaByIdUseCase {
    override suspend fun invoke(id: Int): Result<Zadacha> = withContext(Dispatchers.IO) {
        runCatching {
            repository.getZadachaById(id) ?: throw IllegalArgumentException("Zadacha with id $id not found")
        }
    }
}

