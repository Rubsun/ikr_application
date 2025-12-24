package com.nastyazz.impel.nastyazz.domain
import com.nastyazz.api.domain.usecases.ItemSuggestUseCase
import com.nastyazz.impel.nastyazz.data.ItemRepository

internal class ItemSuggestUseCaseImpl(
    private val repository: ItemRepository
) : ItemSuggestUseCase {
    override suspend fun invoke(): String? {
        return runCatching { repository.savedSuggest() }.getOrNull()
    }
}