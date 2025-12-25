package com.tire.impl.data.repository

import com.tire.api.domain.models.Pokemon
import com.tire.api.domain.models.PokemonCase
import kotlinx.coroutines.flow.Flow

internal interface PokemonRepository {
    // Получить все кейсы из конфигурации
    suspend fun getAllCases(): List<PokemonCase>

    // Получить кейс по ID
    suspend fun getCaseById(caseId: String): PokemonCase?

    // Получить покемона по ID из PokeAPI
    suspend fun getPokemonById(id: Int): Pokemon

    // Получить список покемонов по их ID
    suspend fun getPokemonsByIds(ids: List<Int>): List<Pokemon>

    // Получить всех покемонов (с кэшированием)
    fun getAllPokemons(limit: Int = 151): Flow<List<Pokemon>>

    // Сохранить покемона в коллекцию
    suspend fun savePokemonToCollection(pokemon: Pokemon)

    // Получить коллекцию пользователя
    fun getMyCollection(): Flow<List<Pokemon>>

    // Проверить, есть ли покемон в коллекции
    suspend fun isPokemonOwned(pokemonId: Int): Boolean

    // Получить количество дубликатов покемона
    suspend fun getPokemonDuplicateCount(pokemonId: Int): Int

    // Поиск покемонов по имени
    fun searchPokemons(query: String): Flow<List<Pokemon>>

    suspend fun getAllPokemonList(offset: Int, limit: Int): List<Pokemon>
}
