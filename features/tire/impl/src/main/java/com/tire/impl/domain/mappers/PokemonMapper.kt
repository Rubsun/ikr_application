package com.tire.impl.domain.mappers

import com.tire.api.domain.PokemonRarity
import com.tire.api.domain.PokemonType
import com.tire.api.domain.models.Pokemon
import com.tire.network.api.models.PokemonDetail
import com.tire.storage.api.models.PokemonRecord
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.json.Json

internal object PokemonMapper {

    private val json = Json { ignoreUnknownKeys = true }

    fun toDomain(dto: PokemonDetail, rarity: PokemonRarity, ownedCount: Int = 0): Pokemon {
        return Pokemon(
            id = dto.id,
            name = dto.name.capitalize(),
            imageUrl = dto.imageUrl.orEmpty(),
            types = mapTypes(dto.types),
            rarity = rarity,
            ownedCount = ownedCount
        )
    }

    fun fromRecord(record: PokemonRecord): Pokemon {
        return Pokemon(
            id = record.id,
            name = record.name,
            imageUrl = record.imageUrl,
            types = record.types.toTypeList(),
            rarity = record.rarity.toRarity(),
            ownedCount = record.ownedCount
        )
    }

    fun toRecord(pokemon: Pokemon, firstObtainedAt: Long? = null): PokemonRecord {
        return PokemonRecord(
            id = pokemon.id,
            name = pokemon.name,
            imageUrl = pokemon.imageUrl,
            types = pokemon.types.toTypeString(),
            rarity = pokemon.rarity.name,
            ownedCount = pokemon.ownedCount,
            firstObtainedAt = firstObtainedAt,
        )
    }

    fun fromRecords(records: List<PokemonRecord>): List<Pokemon> {
        return records.map { fromRecord(it) }
    }

    fun toRecords(pokemons: List<Pokemon>): List<PokemonRecord> {
        return pokemons.map { toRecord(it) }
    }

    private fun mapTypes(typeNames: List<String>): List<PokemonType> {
        return typeNames.map { mapTypeName(it) }
    }

    private fun mapTypeName(typeName: String): PokemonType {
        return when (typeName.lowercase()) {
            "normal" -> PokemonType.NORMAL
            "fire" -> PokemonType.FIRE
            "water" -> PokemonType.WATER
            "grass" -> PokemonType.GRASS
            "electric" -> PokemonType.ELECTRIC
            "ice" -> PokemonType.ICE
            "fighting" -> PokemonType.FIGHTING
            "poison" -> PokemonType.POISON
            "ground" -> PokemonType.GROUND
            "flying" -> PokemonType.FLYING
            "psychic" -> PokemonType.PSYCHIC
            "bug" -> PokemonType.BUG
            "rock" -> PokemonType.ROCK
            "ghost" -> PokemonType.GHOST
            "dragon" -> PokemonType.DRAGON
            "dark" -> PokemonType.DARK
            "steel" -> PokemonType.STEEL
            "fairy" -> PokemonType.FAIRY
            else -> PokemonType.NORMAL
        }
    }

    private fun String.capitalize(): String {
        return replaceFirstChar {
            if (it.isLowerCase()) it.titlecase() else it.toString()
        }
    }

    private fun String.toTypeList(): List<PokemonType> {
        return try {
            val typeNames = json.decodeFromString<List<String>>(this)
            typeNames.map { PokemonType.valueOf(it) }
        } catch (_: Exception) {
            emptyList()
        }
    }

    private fun List<PokemonType>.toTypeString(): String {
        return json.encodeToString(
            ListSerializer(String.serializer()),
            this.map { it.name }
        )
    }

    private fun String.toRarity(): PokemonRarity {
        return try {
            PokemonRarity.valueOf(this)
        } catch (_: Exception) {
            PokemonRarity.COMMON
        }
    }
}
