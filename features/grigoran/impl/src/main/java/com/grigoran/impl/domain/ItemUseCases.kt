package com.grigoran.impl.domain

import com.grigoran.api.domain.AddItemUseCase
import com.grigoran.api.domain.ItemSearchUseCase
import com.grigoran.api.domain.ItemSuggestUseCase
import com.grigoran.api.domain.SortItemUseCase
import com.grigoran.api.models.Item
import com.grigoran.api.models.ItemResult
import com.grigoran.impl.data.ItemDto
import com.grigoran.impl.data.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.runCatching

class SortItemsUseCaseImpl: SortItemUseCase {
    override fun invoke(items: List<Item>, ascending: Boolean): List<Item> {
        return if (ascending) {
            items.sortedBy { it.price }
        } else {
            items.sortedByDescending { it.price }
        }
    }
}

//internal class AddItemUseCaseImpl(
//    private val repository: Repository
//) : AddItemUseCase {
//    override fun invoke(title: String, price: Int) {
//        repository.addItem(title, price)
//    }
//}


internal class ItemSearchUseCaseImpl(
    private val repository: Repository
) : ItemSearchUseCase {
    override suspend fun invoke(query: String): ItemResult = withContext(Dispatchers.IO) {
        val result = when {
            query.isBlank() -> Result.success(emptyList())
            else -> runCatching {
                repository.search(query).map(::map)
            }
        }
        if (result.getOrNull()?.isNotEmpty() == true) {
            launch { repository.saveSuggest(query) }
        }

        ItemResult(
            query=query,
            items = result.getOrNull() ?: emptyList(),
            error = result.exceptionOrNull()
        )
    }

    private fun map(dto: ItemDto): Item {
        return Item(
            id = dto.id,
            title = dto.title,
            price = dto.price,
            ItemUrl = dto.thumbnail
        )
    }
}

internal class ItemSuggestUseCaseImpl(
    private val repository: Repository
) : ItemSuggestUseCase {
    override suspend fun invoke(): String? {
        return runCatching { repository.savedSuggest() }.getOrNull()
    }
}