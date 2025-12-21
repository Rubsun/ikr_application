package com.eremin.impl.domain

import com.eremin.impl.data.CapybaraRepository
import com.eremin.api.domain.models.Capybara
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

internal class GetCapybarasUseCase(private val repository: CapybaraRepository) {

    suspend fun execute(from: Int, take: Int): Flow<List<Capybara>> = flow {
        val capybaras = repository.getCapybaras(from, take).map {
            Capybara(it.url, it.alt)
        }
        emit(capybaras)
    }
}
