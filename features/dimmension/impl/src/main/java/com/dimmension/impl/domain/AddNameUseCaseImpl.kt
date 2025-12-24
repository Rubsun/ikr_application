package com.dimmension.impl.domain

import com.dimmension.api.domain.usecases.AddNameUseCase
import com.dimmension.impl.data.NameRepository

internal class AddNameUseCaseImpl(
    private val repository: NameRepository,
) : AddNameUseCase {
    override suspend fun invoke(firstName: String, lastName: String) {
        repository.addName(firstName, lastName)
    }
}


