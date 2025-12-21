package com.vtyapkova.impl.data

import com.example.primitivestorage.api.PrimitiveStorage
import com.vtyapkova.impl.data.storage.StoredViktoriaData
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

internal class ViktoriaRepository(
    private val apiUseCase: com.vtyapkova.impl.domain.LoadUserFromApiUseCase,
    private val storage: PrimitiveStorage<List<StoredViktoriaData>>,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Default,
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

    private val allData = MutableStateFlow<List<StoredViktoriaData>>(emptyList())

    private val scope = CoroutineScope(dispatcher + SupervisorJob())

    init {
        scope.launch {
            try {
                loadFromStorage()
            } catch (_: Throwable) {
                allData.value = generateMultipleViktoria(10)
                saveToStorage()
            }
        }
    }

    fun observeAllData(): Flow<List<StoredViktoriaData>> = allData

    suspend fun addViktoria(firstName: String, lastName: String) {
        withContext(dispatcher) {
            val fullName = "$firstName $lastName"
            val initials = "${firstName.first()}.${lastName.first()}."
            val newData = StoredViktoriaData(
                firstName = firstName,
                lastName = lastName,
                fullName = fullName,
                initials = initials
            )
            allData.update { current -> current + newData }
            saveToStorage()
        }
    }

    suspend fun addViktoriaFromApi() {
        val apiData = apiUseCase.execute().first()
        withContext(dispatcher) {
            allData.update { current -> current + apiData }
            saveToStorage()
        }
    }

    fun getRandomViktoria(): Flow<StoredViktoriaData> {
        return kotlinx.coroutines.flow.flow {
            kotlinx.coroutines.delay(100)
            emit(generateRandomViktoria())
        }
    }

    private suspend fun loadFromStorage() {
        val stored = storage.get().first()
        if (!stored.isNullOrEmpty()) {
            allData.value = stored
        } else {
            allData.value = generateMultipleViktoria(10)
            saveToStorage()
        }
    }

    private suspend fun saveToStorage() {
        storage.put(allData.value)
    }

    private fun generateRandomViktoria(): StoredViktoriaData {
        val firstName = firstNames.random()
        val lastName = lastNames.random()
        val fullName = "$firstName $lastName"
        val initials = "${firstName.first()}.${lastName.first()}."

        return StoredViktoriaData(
            firstName = firstName,
            lastName = lastName,
            fullName = fullName,
            initials = initials
        )
    }

    private fun generateMultipleViktoria(count: Int): List<StoredViktoriaData> {
        return (1..count).map { generateRandomViktoria() }
    }
}
