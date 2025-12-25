package com.rin2396.impl.domain

import com.rin2396.api.domain.models.CatModel
import com.rin2396.api.domain.usecases.GetCatsUseCase
import com.rin2396.impl.data.CatsLocalRepository
import kotlinx.coroutines.flow.Flow

internal class GetCatsUseCaseImpl(
    private val repository: CatsLocalRepository
) : GetCatsUseCase {
    override fun execute(): Flow<List<CatModel>> {
        return repository.getCatsFlow()
    }
}
