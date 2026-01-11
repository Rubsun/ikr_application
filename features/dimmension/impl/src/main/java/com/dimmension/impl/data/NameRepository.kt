package com.dimmension.impl.data

import com.dimmension.impl.data.models.NameRecordDto
import com.dimmension.impl.data.models.NamesStateDto
import com.example.primitivestorage.api.PrimitiveStorage
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.withContext
import kotlin.random.Random

private const val STORAGE_NAME = "dimmension_names.json"
private const val INITIAL_NAMES_COUNT = 5

internal class NameRepository(
    storageFactory: PrimitiveStorage.Factory,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
) {
    private val storage by lazy {
        storageFactory.create(STORAGE_NAME, NamesStateDto.serializer())
    }

    val namesFlow: Flow<List<NameRecordDto>> = storage.get()
        .onStart { ensureInitialData() }
        .map { state -> state?.names.orEmpty() }
        .flowOn(ioDispatcher)

    suspend fun addName(firstName: String, lastName: String): NameRecordDto =
        withContext(ioDispatcher) {
            val record = NameRecordDto(
                firstName = firstName.trim(),
                lastName = lastName.trim(),
            )

            storage.patch { state ->
                val current = state?.names.orEmpty()
                NamesStateDto(names = current + record)
            }

            record
        }

    suspend fun generateRandomName(): NameRecordDto = withContext(ioDispatcher) {
        val record = randomRecord()

        storage.patch { state ->
            val current = state?.names.orEmpty()
            NamesStateDto(names = current + record)
        }

        record
    }

    suspend fun generateBatch(count: Int): List<NameRecordDto> = withContext(ioDispatcher) {
        val generated = (0 until count.coerceAtLeast(1)).map { randomRecord() }

        storage.patch { state ->
            val current = state?.names.orEmpty()
            NamesStateDto(names = current + generated)
        }

        generated
    }

    private suspend fun ensureInitialData() {
        storage.patch { state ->
            if (state == null || state.names.isEmpty()) {
                NamesStateDto(generateSeed(INITIAL_NAMES_COUNT))
            } else {
                state
            }
        }
    }

    private fun generateSeed(count: Int): List<NameRecordDto> {
        return (0 until count).map { randomRecord() }
    }

    private fun randomRecord(): NameRecordDto {
        val firstName = FIRST_NAMES.random(random)
        val lastName = LAST_NAMES.random(random)

        return NameRecordDto(firstName = firstName, lastName = lastName)
    }

    private companion object {
        val random = Random(System.currentTimeMillis())

        val FIRST_NAMES = listOf(
            "Александр", "Дмитрий", "Максим", "Сергей", "Андрей",
            "Алексей", "Артем", "Илья", "Кирилл", "Михаил",
            "Никита", "Матвей", "Роман", "Владимир", "Иван",
            "Анна", "Мария", "Елена", "Ольга", "Татьяна",
            "Наталья", "Ирина", "Светлана", "Екатерина", "Юлия"
        )

        val LAST_NAMES = listOf(
            "Иванов", "Петров", "Сидоров", "Смирнов", "Кузнецов",
            "Попов", "Соколов", "Лебедев", "Козлов", "Новиков",
            "Морозов", "Волков", "Соловьев", "Васильев", "Зайцев",
            "Павлов", "Семенов", "Голубев", "Виноградов", "Богданов"
        )
    }
}


