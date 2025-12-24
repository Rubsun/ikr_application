package com.tire.impl.domain.mappers

import com.tire.api.domain.PokemonRarity
import com.tire.api.domain.PokemonType
import com.tire.api.domain.models.Pokemon
import com.tire.impl.data.local.entities.PokemonEntity
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.json.Json

internal object EntityMapper {

    private val json = Json { ignoreUnknownKeys = true }

    fun toDomain(entity: PokemonEntity): Pokemon {
        return Pokemon(
            id = entity.id,
            name = entity.name,
            imageUrl = entity.imageUrl,
            types = entity.types.toTypeList(),
            rarity = entity.rarity.toRarity(),
            isOwned = entity.isOwned
        )
    }

    fun toEntity(pokemon: Pokemon, duplicateCount: Int = 0, firstObtainedAt: Long? = null): PokemonEntity {
        return PokemonEntity(
            id = pokemon.id,
            name = pokemon.name,
            imageUrl = pokemon.imageUrl,
            types = pokemon.types.toTypeString(),
            rarity = pokemon.rarity.name,
            isOwned = pokemon.isOwned,
            duplicateCount = duplicateCount,
            firstObtainedAt = firstObtainedAt
        )
    }

    fun toDomain(entities: List<PokemonEntity>): List<Pokemon> {
        return entities.map { toDomain(it) }
    }

    fun toEntity(pokemons: List<Pokemon>): List<PokemonEntity> {
        return pokemons.map { toEntity(it) }
    }

    private fun String.toTypeList(): List<PokemonType> {
        return try {
            val typeNames = json.decodeFromString<List<String>>(this)
            typeNames.map { PokemonType.valueOf(it) }
        } catch (e: Exception) {
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
        } catch (e: Exception) {
            PokemonRarity.COMMON
        }
    }
}
