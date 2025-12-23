package com.nastyazz.api.domain.usecases

import com.nastyazz.api.domain.models.Item

interface ItemSearchUseCase {
    suspend operator fun invoke(query: String): List<Item>
}