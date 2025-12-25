package com.rin2396.impl.domain

import com.rin2396.api.domain.models.CatModel
import com.rin2396.api.domain.usecases.SearchCatsUseCase
import com.rin2396.impl.data.CatsLocalRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class SearchCatsUseCaseImpl(
    private val repository: CatsLocalRepository
) : SearchCatsUseCase {
    override fun execute(query: String): Flow<List<CatModel>> {
        return repository.getCatsFlow().map { cats ->
            if (query.isEmpty()) {
                cats
            } else {
                cats.filter { cat ->
                    cat.tag.contains(query, ignoreCase = true)
                }
            }
        }
    }
}
