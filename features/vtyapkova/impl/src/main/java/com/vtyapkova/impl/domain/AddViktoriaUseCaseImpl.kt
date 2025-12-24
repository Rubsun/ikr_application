package com.vtyapkova.impl.domain

import com.vtyapkova.api.domain.usecases.AddViktoriaUseCase
import com.vtyapkova.impl.data.ViktoriaRepository
import kotlinx.coroutines.flow.Flow

internal class AddViktoriaUseCaseImpl(
    private val repository: ViktoriaRepository
) : AddViktoriaUseCase {
    override fun execute(firstName: String, lastName: String): Flow<Unit> {
        return repository.addViktoria(firstName, lastName)
    }
}

