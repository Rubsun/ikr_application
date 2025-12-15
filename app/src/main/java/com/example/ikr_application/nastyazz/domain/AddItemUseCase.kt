package com.example.ikr_application.nastyazz.domain


class AddItemUseCase(
    private val repo: ItemRepository
) {
    suspend operator fun invoke(title: String) {
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
