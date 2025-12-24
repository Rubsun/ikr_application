package com.tire.impl.ui.allpokemons

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.tire.api.domain.models.Pokemon
import com.tire.api.domain.usecases.GetAllPokemonsUseCase

internal class AllPokemonsPagingSource(
    private val getAllPokemonsUseCase: GetAllPokemonsUseCase,
    private val query: String
) : PagingSource<Int, Pokemon>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Pokemon> {
        return try {
            val page = params.key ?: 0
            val pokemons = getAllPokemonsUseCase(page * 20, 20)

            val filtered = if (query.isBlank()) {
                pokemons
            } else {
                pokemons.filter { it.name.contains(query, ignoreCase = true) }
            }

            LoadResult.Page(
                data = filtered,
                prevKey = if (page == 0) null else page - 1,
                nextKey = if (pokemons.isEmpty()) null else page + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Pokemon>): Int? {
        return state.anchorPosition?.let { pos ->
            state.closestPageToPosition(pos)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(pos)?.nextKey?.minus(1)
        }
    }
}

