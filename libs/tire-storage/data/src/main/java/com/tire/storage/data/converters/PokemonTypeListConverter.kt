package com.tire.storage.data.converters

import androidx.room.TypeConverter
import com.tire.api.domain.PokemonType
import kotlinx.serialization.json.Json

internal class PokemonTypeListConverter {

    private val json = Json { ignoreUnknownKeys = true }

    @TypeConverter
    fun fromTypeList(types: List<PokemonType>): String {
        return json.encodeToString(types.map { it.name })
    }

    @TypeConverter
    fun toTypeList(typesString: String): List<PokemonType> {
        return try {
            val typeNames = json.decodeFromString<List<String>>(typesString)
            typeNames.map { PokemonType.valueOf(it) }
        } catch (e: Exception) {
            emptyList()
        }
    }
}
