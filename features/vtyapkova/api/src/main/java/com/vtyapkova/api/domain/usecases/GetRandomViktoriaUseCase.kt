package com.vtyapkova.api.domain.usecases

import com.vtyapkova.api.domain.models.ViktoriaDisplayModel
import kotlinx.coroutines.flow.Flow

interface GetRandomViktoriaUseCase {
    fun execute(): Flow<ViktoriaDisplayModel>
}

