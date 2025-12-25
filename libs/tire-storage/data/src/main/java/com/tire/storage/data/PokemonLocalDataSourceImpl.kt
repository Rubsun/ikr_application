package com.tire.storage.data

import com.tire.storage.api.PokemonLocalDataSource
import com.tire.storage.api.models.PokemonRecord
import com.tire.storage.data.dao.PokemonDao
import com.tire.storage.data.entities.PokemonEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class PokemonLocalDataSourceImpl(
    private val dao: PokemonDao
) : PokemonLocalDataSource {

    override fun getAllPokemons(): Flow<List<PokemonRecord>> =
        dao.getAllPokemons().map { it.map(PokemonEntity::toRecord) }

    override suspend fun getPokemonById(id: Int): PokemonRecord? =
        dao.getPokemonById(id)?.toRecord()

    override suspend fun getPokemonsByIds(ids: List<Int>): List<PokemonRecord> =
        dao.getPokemonsByIds(ids).map { it.toRecord() }

    override fun getMyCollection(): Flow<List<PokemonRecord>> =
        dao.getMyCollection().map { it.map(PokemonEntity::toRecord) }

    override fun searchPokemons(query: String): Flow<List<PokemonRecord>> =
        dao.searchPokemons(query).map { it.map(PokemonEntity::toRecord) }

    override suspend fun isPokemonOwned(id: Int): Boolean =
        dao.getDuplicateCount(id)!! > 0

    override suspend fun getDuplicateCount(id: Int): Int =
        dao.getDuplicateCount(id) ?: 0

    override suspend fun insertPokemon(pokemon: PokemonRecord) =
        dao.insertPokemon(pokemon.toEntity())

    override suspend fun insertPokemons(pokemons: List<PokemonRecord>) =
        dao.insertPokemons(pokemons.map { it.toEntity() })

    override suspend fun addToCollection(id: Int, timestamp: Long) =
        dao.addToCollection(id, timestamp)

    override suspend fun deleteAll() = dao.deleteAll()

    override suspend fun getAllPokemonPage(offset: Int, limit: Int): List<PokemonRecord> =
        dao.getAllPokemonPage(offset, limit).map { it.toRecord() }

    override suspend fun getByName(name: String): PokemonRecord? =
        dao.getByName(name)?.toRecord()
}


private fun PokemonEntity.toRecord() = PokemonRecord(
    id = id,
    name = name,
    imageUrl = imageUrl,
    types = types,
    rarity = rarity,
    ownedCount = ownedCount,
    firstObtainedAt = firstObtainedAt
)

private fun PokemonRecord.toEntity() = PokemonEntity(
    id = id,
    name = name,
    imageUrl = imageUrl,
    types = types,
    rarity = rarity,
    ownedCount = ownedCount,
    firstObtainedAt = firstObtainedAt
)
