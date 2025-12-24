package com.example.ikr_application.vtyapkova.domain

import com.example.ikr_application.vtyapkova.data.ViktoriaRepository
import kotlinx.coroutines.flow.Flow

class AddViktoriaFromApiUseCase {
    private val repository = ViktoriaRepository.INSTANCE

    fun execute(): Flow<Unit> {
        return repository.addViktoriaFromApi()
    }
}

