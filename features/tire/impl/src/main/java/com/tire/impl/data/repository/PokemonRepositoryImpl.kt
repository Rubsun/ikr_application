package com.tire.impl.data.repository

import com.tire.api.domain.models.Pokemon
import com.tire.api.domain.models.PokemonCase
import com.tire.impl.data.config.CaseConfigLoader
import com.tire.impl.domain.mappers.PokemonMapper
import com.tire.impl.domain.utils.RarityCalculator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import com.tire.network.api.PokeRemoteDataSource
import com.tire.storage.api.PokemonLocalDataSource


internal class PokemonRepositoryImpl(
    private val caseConfigLoader: CaseConfigLoader,
    private val pokeApiClient: PokeRemoteDataSource,
    private val local: PokemonLocalDataSource
) : PokemonRepository {

    override suspend fun getAllCases(): List<PokemonCase> {
        return withContext(Dispatchers.IO) {
            caseConfigLoader.loadCases()
        }
    }

    override suspend fun getCaseById(caseId: String): PokemonCase? {
        return withContext(Dispatchers.IO) {
            caseConfigLoader.getCaseById(caseId)
        }
    }

    override suspend fun getPokemonById(id: Int): Pokemon {
        return withContext(Dispatchers.IO) {
            val cachedRecord = local.getPokemonById(id)
            if (cachedRecord != null) {
                return@withContext PokemonMapper.fromRecord(cachedRecord)
            }
            val dto = pokeApiClient.getPokemonById(id)
            val rarity = RarityCalculator.calculateRarity(id)
            val pokemon = PokemonMapper.toDomain(dto, rarity)
            val record = PokemonMapper.toRecord(pokemon)
            local.insertPokemon(record)
            pokemon
        }
    }

    override suspend fun getPokemonsByIds(ids: List<Int>): List<Pokemon> {
        return withContext(Dispatchers.IO) {
            val cachedRecords = local.getPokemonsByIds(ids)
            val cachedIds = cachedRecords.map { it.id }
            val missingIds = ids.filterNot { it in cachedIds }
            val newPokemons = if (missingIds.isNotEmpty()) {
                val dtos = pokeApiClient.getPokemonsByIds(missingIds)
                dtos.map { dto ->
                    val rarity = RarityCalculator.calculateRarity(dto.id)
                    PokemonMapper.toDomain(dto, rarity)
                }
            } else {
                emptyList()
            }
            if (newPokemons.isNotEmpty()) {
                val records = PokemonMapper.toRecords(newPokemons)
                local.insertPokemons(records)
            }
            val allPokemons = PokemonMapper.fromRecords(cachedRecords) + newPokemons
            ids.mapNotNull { id -> allPokemons.find { it.id == id } }
        }
    }

    override fun getAllPokemons(limit: Int): Flow<List<Pokemon>> {
        return local.getAllPokemons().map { records ->
            if (records.isEmpty()) {
                loadPokemonsFromApi(limit)
            }
            PokemonMapper.fromRecords(records)
        }
    }

    private suspend fun loadPokemonsFromApi(limit: Int) {
        withContext(Dispatchers.IO) {
            try {
                val response = pokeApiClient.getPokemonList(limit, 0)
                val pokemons = response.results.chunked(10).flatMap { chunk ->
                    chunk.map { dto ->
                        async {
                            val detailDto = pokeApiClient.getPokemonById(dto.id)
                            val rarity = RarityCalculator.calculateRarity(dto.id)
                            PokemonMapper.toDomain(detailDto, rarity)
                        }
                    }.awaitAll()
                }
                val records = PokemonMapper.toRecords(pokemons)
                local.insertPokemons(records)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }


    override suspend fun savePokemonToCollection(pokemon: Pokemon) {
        withContext(Dispatchers.IO) {
            val existing = local.getPokemonById(pokemon.id)
            if (existing != null) {
                local.addToCollection(
                    id = pokemon.id,
                    timestamp = System.currentTimeMillis(),
                )
            } else {
                val record = PokemonMapper.toRecord(
                    pokemon = pokemon.copy(ownedCount = 1),
                    firstObtainedAt = System.currentTimeMillis()
                )
                local.insertPokemon(record)
            }
        }
    }

    override fun getMyCollection(): Flow<List<Pokemon>> {
        return local.getMyCollection().map { records ->
            PokemonMapper.fromRecords(records)
        }
    }

    override suspend fun isPokemonOwned(pokemonId: Int): Boolean {
        return withContext(Dispatchers.IO) {
            local.isPokemonOwned(pokemonId)
        }
    }

    override suspend fun getPokemonDuplicateCount(pokemonId: Int): Int {
        return withContext(Dispatchers.IO) {
            local.getDuplicateCount(pokemonId)
        }
    }

    override fun searchPokemons(query: String): Flow<List<Pokemon>> {
        return local.searchPokemons(query).map { records ->
            PokemonMapper.fromRecords(records)
        }
    }

    override suspend fun getAllPokemonList(offset: Int, limit: Int): List<Pokemon> = withContext(Dispatchers.IO) {
        val cachedPage = local.getAllPokemonPage(offset, limit)
        val cachedById = cachedPage.associateBy { it.id }
        val listResponse = pokeApiClient.getPokemonList(limit, offset)  // только «summary»
        val ids = listResponse.results.mapNotNull { result ->
            result.imageUrl?.trimEnd('/')?.substringAfterLast('/')?.toIntOrNull()
        }
        val missingIds = ids.filterNot { id -> cachedById.containsKey(id) }
        val newPokemons: List<Pokemon> =
            if (missingIds.isNotEmpty()) {
                missingIds.mapNotNull { id ->
                    try {
                        val dto = pokeApiClient.getPokemonById(id)
                        val rarity = RarityCalculator.calculateRarity(dto.id)
                        PokemonMapper.toDomain(dto, rarity)
                    } catch (_: Exception) {
                        null
                    }
                }
            } else emptyList()
        if (newPokemons.isNotEmpty()) {
            val entities = PokemonMapper.toRecords(newPokemons)
            local.insertPokemons(entities)
        }
        val finalPage = local.getAllPokemonPage(offset, limit)
        PokemonMapper.fromRecords(finalPage)
    }
}
