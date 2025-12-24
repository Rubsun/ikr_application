package com.nastyazz.api.domain.usecases

interface ItemSuggestUseCase {
    suspend operator fun invoke(): String?
}