package com.vtyapkova.impl.data.storage

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.vtyapkova.impl.data.models.ViktoriaData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext

internal class ViktoriaStorage(
    private val prefs: SharedPreferences
) {
    private val _allData = MutableStateFlow<List<ViktoriaData>>(emptyList())
    val allData: Flow<List<ViktoriaData>> = _allData.asStateFlow()

    suspend fun loadData() = withContext(Dispatchers.IO) {
        val dataJson = prefs.getString(KEY_DATA, null)
        if (dataJson != null && dataJson.isNotEmpty()) {
            // Простое сохранение через разделители
            val items = dataJson.split(SEPARATOR).mapNotNull { line ->
                val parts = line.split(FIELD_SEP)
                if (parts.size == 4) {
                    ViktoriaData(
                        firstName = parts[0],
                        lastName = parts[1],
                        fullName = parts[2],
                        initials = parts[3]
                    )
                } else null
            }
            _allData.value = items
        } else {
            // Инициализация начальными данными
            val initialData = generateInitialData()
            _allData.value = initialData
            saveData(initialData)
        }
    }

    suspend fun saveData(data: List<ViktoriaData>) = withContext(Dispatchers.IO) {
        val dataJson = data.joinToString(SEPARATOR) { item ->
            "${item.firstName}$FIELD_SEP${item.lastName}$FIELD_SEP${item.fullName}$FIELD_SEP${item.initials}"
        }
        prefs.edit { putString(KEY_DATA, dataJson) }
        _allData.value = data
    }

    suspend fun addItem(item: ViktoriaData) = withContext(Dispatchers.IO) {
        val currentData = _allData.value + item
        saveData(currentData)
    }

    fun getCurrentData(): List<ViktoriaData> = _allData.value

    private fun generateInitialData(): List<ViktoriaData> {
        val firstNames = listOf(
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

        val lastNames = listOf(
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

        return (1..10).map {
            val firstName = firstNames.random()
            val lastName = lastNames.random()
            val fullName = "$firstName $lastName"
            val initials = "${firstName.first()}.${lastName.first()}."
            ViktoriaData(
                firstName = firstName,
                lastName = lastName,
                fullName = fullName,
                initials = initials
            )
        }
    }

    companion object {
        private const val PREFS_NAME = "vtyapkova_prefs"
        private const val KEY_DATA = "viktoria_data"
        private const val SEPARATOR = "|||"
        private const val FIELD_SEP = ":::"
    }
}

