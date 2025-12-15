package com.example.ikr_application.eremin.domain

import com.example.ikr_application.eremin.data.CapybaraRepository
import com.example.ikr_application.eremin.domain.models.Capybara
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetCapybarasUseCase(private val repository: CapybaraRepository) {

    suspend fun execute(from: Int, take: Int): Flow<List<Capybara>> = flow {
        val capybaras = repository.getCapybaras(from, take).map {
            Capybara(it.url, it.alt)
        }
        emit(capybaras)
    }
}
