package com.vtyapkova.api.domain.usecases

import kotlinx.coroutines.flow.Flow

interface AddViktoriaUseCase {
    fun execute(firstName: String, lastName: String): Flow<Unit>
}

