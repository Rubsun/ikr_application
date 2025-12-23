package com.dimmension.api.domain.usecases

import com.dimmension.api.domain.models.NameDisplayModel

interface FilterNamesUseCase {
    suspend operator fun invoke(
        names: List<NameDisplayModel>,
        query: String,
    ): List<NameDisplayModel>
}


