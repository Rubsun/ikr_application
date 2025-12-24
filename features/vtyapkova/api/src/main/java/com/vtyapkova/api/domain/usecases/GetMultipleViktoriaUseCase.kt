package com.vtyapkova.api.domain.usecases

import com.vtyapkova.api.domain.models.ViktoriaDisplayModel
import kotlinx.coroutines.flow.Flow

interface GetMultipleViktoriaUseCase {
    fun execute(): Flow<List<ViktoriaDisplayModel>>
}

