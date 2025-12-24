package com.tire.impl.domain.mappers

import com.tire.api.domain.PokemonRarity
import com.tire.api.domain.PokemonType
import com.tire.api.domain.models.Pokemon
import com.tire.network.api.models.PokemonDetail

internal object PokemonMapper {

    fun toDomain(dto: PokemonDetail, rarity: PokemonRarity, isOwned: Boolean = false): Pokemon {
        return Pokemon(
            id = dto.id,
            name = dto.name.capitalize(),
            imageUrl = dto.imageUrl.orEmpty(),
            types = mapTypes(dto.types),
            rarity = rarity,
            isOwned = isOwned
        )
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
}
