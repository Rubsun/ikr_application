package com.nastyazz.api.domain.usecases

interface AddItemUseCase {
    suspend operator fun invoke(title: String)
}
