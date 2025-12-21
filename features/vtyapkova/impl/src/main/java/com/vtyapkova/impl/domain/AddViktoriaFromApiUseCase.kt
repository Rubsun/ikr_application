package com.vtyapkova.impl.domain

import com.vtyapkova.impl.data.ViktoriaRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

internal class AddViktoriaFromApiUseCase(
    private val repository: ViktoriaRepository,
) {
    fun execute(): Flow<Unit> = flow {
        repository.addViktoriaFromApi()
        emit(Unit)
    }.flowOn(kotlinx.coroutines.Dispatchers.IO)
}
