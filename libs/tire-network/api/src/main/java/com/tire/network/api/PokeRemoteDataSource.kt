package com.tire.network.api

import com.tire.network.api.models.PokemonListResponse
import com.tire.network.api.models.PokemonDetail

interface PokeRemoteDataSource {
    suspend fun getPokemonList(limit: Int, offset: Int): PokemonListResponse
    suspend fun getPokemonById(id: Int): PokemonDetail
    suspend fun getPokemonByName(name: String): PokemonDetail
    suspend fun getPokemonsByIds(ids: List<Int>): List<PokemonDetail>
}
