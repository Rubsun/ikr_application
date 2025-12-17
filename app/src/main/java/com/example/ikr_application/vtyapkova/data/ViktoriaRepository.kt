package com.example.ikr_application.vtyapkova.data

import com.example.ikr_application.vtyapkova.data.models.ViktoriaData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

class ViktoriaRepository(
    private val loadUserFromApiUseCase: com.example.ikr_application.vtyapkova.domain.LoadUserFromApiUseCase? = null
) {
    private val firstNames = listOf(
        "James", "John", "Robert", "Michael", "William",
        "David", "Richard", "Joseph", "Thomas", "Charles",
        "Christopher", "Daniel", "Matthew", "Anthony", "Mark",
        "Donald", "Steven", "Paul", "Andrew", "Joshua",
        "Kenneth", "Kevin", "Brian", "George", "Edward",
        "Ronald", "Timothy", "Jason", "Jeffrey", "Ryan",
        "Jacob", "Gary", "Nicholas", "Eric", "Jonathan",
        "Stephen", "Larry", "Justin", "Scott", "Brandon",
        "Benjamin", "Samuel", "Frank", "Gregory", "Raymond",
        "Alexander", "Patrick", "Jack", "Dennis", "Jerry",
        "Tyler", "Aaron", "Jose", "Henry", "Adam",
        "Douglas", "Nathan", "Zachary", "Kyle", "Noah",
        "Ethan", "Jeremy", "Walter", "Christian", "Keith",
        "Emma", "Olivia", "Sophia", "Isabella", "Ava",
        "Mia", "Charlotte", "Amelia", "Harper", "Evelyn",
        "Abigail", "Emily", "Elizabeth", "Mila", "Ella",
        "Avery", "Sofia", "Camila", "Aria", "Scarlett",
        "Victoria", "Madison", "Luna", "Grace", "Chloe",
        "Penelope", "Layla", "Riley", "Zoey", "Nora",
        "Lily", "Eleanor", "Hannah", "Lillian", "Addison",
        "Aubrey", "Ellie", "Stella", "Natalie", "Zoe",
        "Leah", "Hazel", "Violet", "Aurora", "Savannah",
        "Audrey", "Brooklyn", "Bella", "Claire", "Skylar"
    )

    private val lastNames = listOf(
        "Smith", "Johnson", "Williams", "Brown", "Jones",
        "Garcia", "Miller", "Davis", "Rodriguez", "Martinez",
        "Hernandez", "Lopez", "Wilson", "Anderson", "Thomas",
        "Taylor", "Moore", "Jackson", "Martin", "Lee",
        "Thompson", "White", "Harris", "Sanchez", "Clark",
        "Ramirez", "Lewis", "Robinson", "Walker", "Young",
        "Allen", "King", "Wright", "Scott", "Torres",
        "Nguyen", "Hill", "Flores", "Green", "Adams",
        "Nelson", "Baker", "Hall", "Rivera", "Campbell",
        "Mitchell", "Carter", "Roberts", "Gomez", "Phillips",
        "Evans", "Turner", "Diaz", "Parker", "Cruz",
        "Edwards", "Collins", "Reyes", "Stewart", "Morris",
        "Morales", "Murphy", "Cook", "Rogers", "Gutierrez",
        "Ortiz", "Morgan", "Cooper", "Peterson", "Bailey",
        "Reed", "Kelly", "Howard", "Ramos", "Kim",
        "Cox", "Ward", "Richardson", "Watson", "Brooks",
        "Chavez", "Wood", "James", "Bennett", "Gray",
        "Mendoza", "Ruiz", "Hughes", "Price", "Alvarez",
        "Castillo", "Sanders", "Patel", "Myers", "Long",
        "Ross", "Foster", "Jimenez", "Powell", "Jenkins",
        "Perry", "Russell", "Sullivan", "Bell", "Coleman"
    )

    private val _allData = MutableStateFlow<List<ViktoriaData>>(emptyList())
    val allData: StateFlow<List<ViktoriaData>> = _allData.asStateFlow()

    init {
        // Инициализация начальными данными
        _allData.value = generateMultipleViktoria(10)
    }

    private fun generateRandomViktoria(): ViktoriaData {
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

    private fun generateMultipleViktoria(count: Int): List<ViktoriaData> {
        return (1..count).map { generateRandomViktoria() }
    }

    fun getAllData(): Flow<List<ViktoriaData>> = _allData.asStateFlow()

    fun addViktoria(firstName: String, lastName: String): Flow<Unit> = flow {
        // Имитация тяжелой операции
        kotlinx.coroutines.delay(200)
        val fullName = "$firstName $lastName"
        val initials = "${firstName.first()}.${lastName.first()}."
        val newData = ViktoriaData(
            firstName = firstName,
            lastName = lastName,
            fullName = fullName,
            initials = initials
        )
        _allData.value = _allData.value + newData
        emit(Unit)
    }.flowOn(Dispatchers.Default)

    fun addViktoriaFromApi(): Flow<Unit> = flow {
        loadUserFromApiUseCase?.let { useCase ->
            val userData = useCase.execute().first()
            _allData.value = _allData.value + userData
        }
        emit(Unit)
    }.flowOn(Dispatchers.IO)

    fun filterData(query: String): Flow<List<ViktoriaData>> = flow {
        // Имитация тяжелой операции фильтрации
        kotlinx.coroutines.delay(150)
        val result = if (query.isBlank()) {
            _allData.value
        } else {
            val lowerQuery = query.lowercase()
            _allData.value.filter {
                it.firstName.lowercase().contains(lowerQuery) ||
                it.lastName.lowercase().contains(lowerQuery) ||
                it.fullName.lowercase().contains(lowerQuery)
            }
        }
        emit(result)
    }.flowOn(Dispatchers.Default)

    fun getRandomViktoria(): Flow<ViktoriaData> = flow {
        // Имитация тяжелой операции
        kotlinx.coroutines.delay(100)
        emit(generateRandomViktoria())
    }.flowOn(Dispatchers.Default)

    companion object {
        @androidx.annotation.Discouraged("Only for example")
        val INSTANCE = ViktoriaRepository(
            loadUserFromApiUseCase = com.example.ikr_application.vtyapkova.domain.LoadUserFromApiUseCase()
        )
    }
}

