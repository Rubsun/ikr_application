package com.example.ikr_application.dimmension.data

import com.example.ikr_application.dimmension.data.models.NameData

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

    fun generateRandomName(): NameData {
        val firstName = firstNames.random()
        val lastName = lastNames.random()
        val fullName = "$firstName $lastName"
        val initials = "${firstName.first()}.${lastName.first()}."

        return NameData(
            firstName = firstName,
            lastName = lastName,
            fullName = fullName,
            initials = initials
        )
    }

    fun generateMultipleNames(count: Int): List<NameData> {
        return (1..count).map { generateRandomName() }
    }

    companion object {
        @androidx.annotation.Discouraged("Only for example")
        val INSTANCE = NameRepository()
    }
}

