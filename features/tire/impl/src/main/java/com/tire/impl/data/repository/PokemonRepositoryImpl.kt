package com.tire.impl.data.repository

import com.tire.api.domain.models.Pokemon
import com.tire.api.domain.models.PokemonCase
import com.tire.impl.data.config.CaseConfigLoader
import com.tire.impl.data.local.dao.PokemonDao
import com.tire.impl.domain.mappers.EntityMapper
import com.tire.impl.domain.mappers.PokemonMapper
import com.tire.impl.domain.utils.RarityCalculator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import com.tire.network.api.PokeRemoteDataSource


internal class PokemonRepositoryImpl(
    private val caseConfigLoader: CaseConfigLoader,
    private val pokeApiClient: PokeRemoteDataSource,
    private val pokemonDao: PokemonDao
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
            val cachedEntity = pokemonDao.getPokemonById(id)
            if (cachedEntity != null) {
                return@withContext EntityMapper.toDomain(cachedEntity)
            }
            val dto = pokeApiClient.getPokemonById(id)
            val rarity = RarityCalculator.calculateRarity(id)
            val pokemon = PokemonMapper.toDomain(dto, rarity)
            val entity = EntityMapper.toEntity(pokemon)
            pokemonDao.insertPokemon(entity)
            pokemon
        }
    }

    override suspend fun getPokemonsByIds(ids: List<Int>): List<Pokemon> {
        return withContext(Dispatchers.IO) {
            val cachedEntities = pokemonDao.getPokemonsByIds(ids)
            val cachedIds = cachedEntities.map { it.id }
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
                val entities = EntityMapper.toEntity(newPokemons)
                pokemonDao.insertPokemons(entities)
            }
            val allPokemons = EntityMapper.toDomain(cachedEntities) + newPokemons
            ids.mapNotNull { id -> allPokemons.find { it.id == id } }
        }
    }

    override fun getAllPokemons(limit: Int): Flow<List<Pokemon>> {
        return pokemonDao.getAllPokemons().map { entities ->
            if (entities.isEmpty()) {
                loadPokemonsFromApi(limit)
            }
            EntityMapper.toDomain(entities)
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
                val entities = EntityMapper.toEntity(pokemons)
                pokemonDao.insertPokemons(entities)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }


    override suspend fun savePokemonToCollection(pokemon: Pokemon) {
        withContext(Dispatchers.IO) {
            val existing = pokemonDao.getPokemonById(pokemon.id)
            if (existing != null) {
                pokemonDao.addToCollection(
                    pokemonId = pokemon.id,
                    timestamp = System.currentTimeMillis()
                )
            } else {
                val entity = EntityMapper.toEntity(
                    pokemon = pokemon.copy(isOwned = true),
                    duplicateCount = 1,
                    firstObtainedAt = System.currentTimeMillis()
                )
                pokemonDao.insertPokemon(entity)
            }
        }
    }

    override fun getMyCollection(): Flow<List<Pokemon>> {
        return pokemonDao.getMyCollection().map { entities ->
            EntityMapper.toDomain(entities)
        }
    }

    override suspend fun isPokemonOwned(pokemonId: Int): Boolean {
        return withContext(Dispatchers.IO) {
            pokemonDao.isPokemonOwned(pokemonId) ?: false
        }
    }

    override suspend fun getPokemonDuplicateCount(pokemonId: Int): Int {
        return withContext(Dispatchers.IO) {
            pokemonDao.getDuplicateCount(pokemonId) ?: 0
        }
    }

    override fun searchPokemons(query: String): Flow<List<Pokemon>> {
        return pokemonDao.searchPokemons(query).map { entities ->
            EntityMapper.toDomain(entities)
        }
    }

    override suspend fun getAllPokemonList(offset: Int, limit: Int): List<Pokemon> = withContext(Dispatchers.IO) {
        val cachedPage = pokemonDao.getAllPokemonPage(offset, limit)
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
            val entities = EntityMapper.toEntity(newPokemons)
            pokemonDao.insertPokemons(entities)
        }
        val finalPage = pokemonDao.getAllPokemonPage(offset, limit)
        EntityMapper.toDomain(finalPage)
    }
}
