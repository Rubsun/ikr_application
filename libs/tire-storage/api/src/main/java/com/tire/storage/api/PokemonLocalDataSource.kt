package com.tire.storage.api

import kotlinx.coroutines.flow.Flow
import com.tire.storage.api.models.PokemonRecord

interface PokemonLocalDataSource {
    fun getAllPokemons(): Flow<List<PokemonRecord>>
    suspend fun getPokemonById(id: Int): PokemonRecord?
    suspend fun getPokemonsByIds(ids: List<Int>): List<PokemonRecord>
    fun getMyCollection(): Flow<List<PokemonRecord>>
    fun searchPokemons(query: String): Flow<List<PokemonRecord>>
    suspend fun isPokemonOwned(id: Int): Boolean
    suspend fun getDuplicateCount(id: Int): Int
    suspend fun insertPokemon(pokemon: PokemonRecord)
    suspend fun insertPokemons(pokemons: List<PokemonRecord>)
    suspend fun addToCollection(id: Int, timestamp: Long)
    suspend fun deleteAll()
    suspend fun getAllPokemonPage(offset: Int, limit: Int): List<PokemonRecord>
    suspend fun getByName(name: String): PokemonRecord?
}

