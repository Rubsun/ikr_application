package com.grigoran.api.domain

interface ItemSuggestUseCase {
    suspend operator fun invoke(): String?
}