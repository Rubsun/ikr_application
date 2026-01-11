package com.nastyazz.impel.nastyazz.domain
import com.nastyazz.api.domain.models.Item
import com.nastyazz.api.domain.usecases.ItemSearchUseCase
import com.nastyazz.impel.nastyazz.data.ItemRepository

internal class ItemSearchUseCaseImpl(
    private val repository: ItemRepository
) : ItemSearchUseCase {

    override suspend fun invoke(query: String): List<Item> {
        if (query.isBlank()) return emptyList()

        val items = repository.search(query).map {
            Item(
                id = it.id,
                title = it.title,
                description = it.description,
                imageUrl = it.thumbnail
            )
        }

        if (items.isNotEmpty()) {
            repository.saveSuggest(query)
        }

        return items
    }
}