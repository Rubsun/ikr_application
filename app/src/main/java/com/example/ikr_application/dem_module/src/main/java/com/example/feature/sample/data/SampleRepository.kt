package com.example.ikr_application.dem_module.data
import kotlinx.coroutines.delay

class SampleRepository {

    private val fakeItems = List(15) { index ->
        SampleEntity(
            id = index,
            title = "Элемент #${index + 1}",
            description = "Описание элемента номер ${index + 1}. " +
                    "Это фейковые данные для демонстрации работы архитектуры Clean Architecture."
        )
    }

    suspend fun getItems(): List<SampleEntity> {
        delay(800) // Имитация задержки сети/БД
        return fakeItems
    }

    suspend fun getItemById(id: Int): SampleEntity? {
        delay(300)
        return fakeItems.firstOrNull { it.id == id }
    }

    suspend fun searchItems(query: String): List<SampleEntity> {
        delay(500)
        return fakeItems.filter {
            it.title.contains(query, ignoreCase = true) ||
                    it.description.contains(query, ignoreCase = true)
        }
    }
}
