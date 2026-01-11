package com.tire.network.data

import com.example.network.api.RetrofitServiceFactory
import com.tire.network.api.PokeRemoteDataSource
import com.tire.network.api.models.PokemonDetail
import com.tire.network.api.models.PokemonListResponse
import com.tire.network.data.api.PokeApiService
import com.tire.network.data.mappers.toApiModel

internal class PokeApiClient(
    retrofitServiceFactory: RetrofitServiceFactory
) : PokeRemoteDataSource {

    companion object {
        private const val BASE_URL = "https://pokeapi.co/api/v2/"
    }

    private val apiService: PokeApiService = retrofitServiceFactory.create(
        baseUrl = BASE_URL,
        service = PokeApiService::class.java
    )

    override suspend fun getPokemonList(limit: Int, offset: Int): PokemonListResponse {
        val dto = apiService.getPokemonList(limit, offset)
        return dto.toApiModel()
    }

    override suspend fun getPokemonById(id: Int): PokemonDetail {
        val dto = apiService.getPokemonById(id)
        return dto.toApiModel()
    }

    override suspend fun getPokemonByName(name: String): PokemonDetail {
        val dto = apiService.getPokemonByName(name)
        return dto.toApiModel()
    }

    override suspend fun getPokemonsByIds(ids: List<Int>): List<PokemonDetail> {
        return ids.map { id -> getPokemonById(id) }
    }
}
