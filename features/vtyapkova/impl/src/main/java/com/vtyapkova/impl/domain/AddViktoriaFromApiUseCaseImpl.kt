package com.vtyapkova.impl.domain

import com.vtyapkova.api.domain.usecases.AddViktoriaFromApiUseCase
import com.vtyapkova.impl.data.ViktoriaRepository
import kotlinx.coroutines.flow.Flow

internal class AddViktoriaFromApiUseCaseImpl(
    private val repository: ViktoriaRepository
) : AddViktoriaFromApiUseCase {
    override fun execute(): Flow<Unit> {
        return repository.addViktoriaFromApi()
    }
}

