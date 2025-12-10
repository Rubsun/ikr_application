package com.example.ikr_application.vtyapkova.data

import com.example.ikr_application.vtyapkova.data.models.ViktoriaData

class ViktoriaRepository {
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

    fun generateRandomViktoria(): ViktoriaData {
        val firstName = firstNames.random()
        val lastName = lastNames.random()
        val fullName = "$firstName $lastName"
        val initials = "${firstName.first()}.${lastName.first()}."

        return ViktoriaData(
            firstName = firstName,
            lastName = lastName,
            fullName = fullName,
            initials = initials
        )
    }

    fun generateMultipleViktoria(count: Int): List<ViktoriaData> {
        return (1..count).map { generateRandomViktoria() }
    }

    companion object {
        @androidx.annotation.Discouraged("Only for example")
        val INSTANCE = ViktoriaRepository()
    }
}

