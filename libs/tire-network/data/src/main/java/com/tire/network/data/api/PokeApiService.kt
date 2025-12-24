package com.tire.network.data.api

import com.tire.network.data.dto.PokemonDetailDto
import com.tire.network.data.dto.PokemonListResponseDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

internal interface PokeApiService {

    @GET("pokemon")
    suspend fun getPokemonList(
        @Query("limit") limit: Int = 20,
        @Query("offset") offset: Int = 0
    ): PokemonListResponseDto

    @GET("pokemon/{id}")
    suspend fun getPokemonById(
        @Path("id") id: Int
    ): PokemonDetailDto

    @GET("pokemon/{name}")
    suspend fun getPokemonByName(
        @Path("name") name: String
    ): PokemonDetailDto
}
