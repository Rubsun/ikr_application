package com.nastyazz.impel.nastyazz.domain

import com.nastyazz.api.domain.usecases.AddItemUseCase
import com.nastyazz.api.domain.models.Item
import com.nastyazz.impel.nastyazz.data.ItemRepository
import kotlin.random.Random

internal class AddItemUseCaseImpl(
    private val repo: ItemRepository
) : AddItemUseCase {
    override suspend fun invoke(title: String) {
        repo.addItem(
            Item(
                id = Random.nextInt(),
                title = title,
                description = "Добавлено пользователем",
                imageUrl = "https://picsum.photos/200"
            )
        )
    }
}
