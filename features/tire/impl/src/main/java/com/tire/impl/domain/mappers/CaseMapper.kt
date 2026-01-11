package com.tire.impl.domain.mappers

import com.tire.api.domain.PokemonRarity
import com.tire.api.domain.models.LootEntry
import com.tire.api.domain.models.PokemonCase
import com.tire.impl.data.config.dto.LootEntryDto
import com.tire.impl.data.config.dto.PokemonCaseDto

internal object CaseMapper {

    fun toDomain(dto: PokemonCaseDto): PokemonCase {
        return PokemonCase(
            id = dto.id,
            name = dto.name,
            description = dto.description,
            imageUrl = dto.imageUrl,
            lootTable = dto.lootTable.map { it.toDomain() }
        )
    }

    fun toDomain(dtos: List<PokemonCaseDto>): List<PokemonCase> {
        return dtos.map { toDomain(it) }
    }

    private fun LootEntryDto.toDomain(): LootEntry {
        return LootEntry(
            pokemonIds = pokemonIds,
            rarity = PokemonRarity.valueOf(rarity.toString()),
            dropChance = dropChance
        )
    }
}
