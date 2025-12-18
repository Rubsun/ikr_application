package com.nastyazz.impel.nastyazz.domain

import com.nastyazz.impel.nastyazz.data.ItemDto
import com.nastyazz.impel.nastyazz.data.ItemRepository
import kotlin.random.Random
import com.nastyazz.api.domain.usecases.AddItemUseCase

internal class AddItemUseCaseImpl(
    private val repo: ItemRepository
) : AddItemUseCase {

    override suspend fun invoke(title: String) {
        repo.addItem(
            ItemDto(
                id = Random.nextInt(),
                title = title,
                description = "Добавлено пользователем",
                imageUrl = "https://picsum.photos/200"
            )
        )
    }
}