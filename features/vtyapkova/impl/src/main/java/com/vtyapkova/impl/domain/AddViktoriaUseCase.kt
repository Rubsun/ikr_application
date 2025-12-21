package com.vtyapkova.impl.domain

import com.vtyapkova.impl.data.ViktoriaRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

internal class AddViktoriaUseCase(
    private val repository: ViktoriaRepository,
) {
    fun execute(firstName: String, lastName: String): Flow<Unit> = flow {
        delay(200)
        repository.addViktoria(firstName, lastName)
        emit(Unit)
    }.flowOn(Dispatchers.Default)
}
