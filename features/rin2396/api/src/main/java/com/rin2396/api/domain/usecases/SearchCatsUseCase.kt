package com.rin2396.api.domain.usecases

import com.rin2396.api.domain.models.CatModel
import kotlinx.coroutines.flow.Flow

interface SearchCatsUseCase {
    fun execute(query: String): Flow<List<CatModel>>
}
