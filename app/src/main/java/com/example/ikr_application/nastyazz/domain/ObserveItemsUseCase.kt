package com.example.ikr_application.nastyazz.domain


class ObserveItemsUseCase(
    private val repo: ItemRepository
) {
    operator fun invoke(): StateFlow<List<Item>> =
        repo.observeItems()
            .map { list ->
                list.map {
                    Item(it.id, it.title, it.description, it.imageUrl)
                }
            }
            .stateIn(
                CoroutineScope(Dispatchers.Default),
                SharingStarted.Eagerly,
                emptyList()
            )
}
