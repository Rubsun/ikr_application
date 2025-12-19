package com.example.ikr_application.vtyapkova.domain

import com.example.ikr_application.vtyapkova.data.ViktoriaRepository
import kotlinx.coroutines.flow.Flow

class AddViktoriaUseCase {
    private val repository = ViktoriaRepository.INSTANCE

    fun execute(firstName: String, lastName: String): Flow<Unit> {
        return repository.addViktoria(firstName, lastName)
    }
}

