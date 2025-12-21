package com.dimmension.api.domain.usecases

interface AddNameUseCase {
    suspend operator fun invoke(firstName: String, lastName: String)
}


