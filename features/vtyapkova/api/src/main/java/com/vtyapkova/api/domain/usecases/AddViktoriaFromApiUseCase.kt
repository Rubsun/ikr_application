package com.vtyapkova.api.domain.usecases

import kotlinx.coroutines.flow.Flow

interface AddViktoriaFromApiUseCase {
    fun execute(): Flow<Unit>
}

