package com.dimmension.impl.domain

import com.dimmension.api.domain.models.NameDisplayModel
import com.dimmension.api.domain.usecases.FilterNamesUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

internal class FilterNamesUseCaseImpl : FilterNamesUseCase {
    override suspend fun invoke(
        names: List<NameDisplayModel>,
        query: String,
    ): List<NameDisplayModel> = withContext(Dispatchers.Default) {
        if (query.isBlank()) {
            names
        } else {
            val lowerQuery = query.lowercase()
            names.filter { name ->
                name.displayName.lowercase().contains(lowerQuery) ||
                    name.shortName.lowercase().contains(lowerQuery) ||
                    name.initials.lowercase().contains(lowerQuery)
            }
        }
    }
}


