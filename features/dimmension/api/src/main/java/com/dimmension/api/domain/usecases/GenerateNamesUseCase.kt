package com.dimmension.api.domain.usecases

import com.dimmension.api.domain.models.NameDisplayModel

interface GenerateNamesUseCase {
    suspend operator fun invoke(count: Int = 5): List<NameDisplayModel>
}


