package com.example.ikr_application.dimmension.data

import com.example.ikr_application.dimmension.data.models.NameData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.withContext

class NameRepository {
    private val firstNames = listOf(
        "Александр", "Дмитрий", "Максим", "Сергей", "Андрей",
        "Алексей", "Артем", "Илья", "Кирилл", "Михаил",
        "Никита", "Матвей", "Роман", "Владимир", "Иван",
        "Анна", "Мария", "Елена", "Ольга", "Татьяна",
        "Наталья", "Ирина", "Светлана", "Екатерина", "Юлия"
    )

    private val lastNames = listOf(
        "Иванов", "Петров", "Сидоров", "Смирнов", "Кузнецов",
        "Попов", "Соколов", "Лебедев", "Козлов", "Новиков",
        "Морозов", "Волков", "Соловьев", "Васильев", "Зайцев",
        "Павлов", "Семенов", "Голубев", "Виноградов", "Богданов"
    )

    private val _namesList = MutableStateFlow<List<NameData>>(emptyList())
    val namesList: StateFlow<List<NameData>> = _namesList.asStateFlow()

    init {
        // Инициализируем список несколькими именами синхронно
        _namesList.value = generateMultipleNamesSync(5)
    }

    private fun generateMultipleNamesSync(count: Int): List<NameData> {
        return (1..count).map {
            val firstName = firstNames.random()
            val lastName = lastNames.random()
            val fullName = "$firstName $lastName"
            val initials = "${firstName.first()}.${lastName.first()}."
            NameData(
                firstName = firstName,
                lastName = lastName,
                fullName = fullName,
                initials = initials
            )
        }
    }

    suspend fun generateRandomName(): NameData = withContext(Dispatchers.Default) {
        delay(100) // Имитация тяжелой операции
        val firstName = firstNames.random()
        val lastName = lastNames.random()
        val fullName = "$firstName $lastName"
        val initials = "${firstName.first()}.${lastName.first()}."

        NameData(
            firstName = firstName,
            lastName = lastName,
            fullName = fullName,
            initials = initials
        )
    }

    suspend fun generateMultipleNames(count: Int): List<NameData> = withContext(Dispatchers.Default) {
        delay(200) // Имитация тяжелой операции
        (1..count).map {
            val firstName = firstNames.random()
            val lastName = lastNames.random()
            val fullName = "$firstName $lastName"
            val initials = "${firstName.first()}.${lastName.first()}."
            NameData(
                firstName = firstName,
                lastName = lastName,
                fullName = fullName,
                initials = initials
            )
        }
    }

    suspend fun addName(nameData: NameData) = withContext(Dispatchers.IO) {
        delay(50) // Имитация операции записи
        _namesList.value = _namesList.value + nameData
    }

    fun getAllNames(): Flow<List<NameData>> = namesList

    companion object {
        @androidx.annotation.Discouraged("Only for example")
        val INSTANCE = NameRepository()
    }
}

