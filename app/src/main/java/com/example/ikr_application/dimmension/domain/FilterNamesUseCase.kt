package com.example.ikr_application.dimmension.domain

import com.example.ikr_application.dimmension.domain.models.NameDisplayModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class FilterNamesUseCase {
    suspend fun execute(names: List<NameDisplayModel>, query: String): List<NameDisplayModel> = 
        withContext(Dispatchers.Default) {
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

