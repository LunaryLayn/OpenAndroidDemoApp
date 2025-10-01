/*
 * © 2025 Hugo Polo - Github: https://github.com/LunaryLayn
 */

package com.hugopolog.data.util

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Optional
import com.hugopolog.data.graphql.GetAllPokemonQuery
import com.hugopolog.data.mapper.toDto
import com.hugopolog.domain.entities.pokemon.PokemonListModel

class PokemonApolloPagingSource(
    private val apolloClient: ApolloClient,
    private val pageSize: Int,
    val name: String? = null,                // make nullable
    val type: List<String>? = emptyList()    // make nullable
) : PagingSource<Int, PokemonListModel>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PokemonListModel> {
        val offset = params.key ?: 0

        return try {
            val response = apolloClient.query(
                GetAllPokemonQuery(
                    limit = pageSize, offset = offset,
                    name = Optional.presentIfNotNull(name),
                    types = Optional.presentIfNotNull(type)
                )
            ).execute()

            val pokemons = response.data?.pokemons?.map { p -> p.toDto() } ?: emptyList()

            LoadResult.Page(
                data = pokemons,
                prevKey = if (offset == 0) null else offset - pageSize,
                nextKey = if (pokemons.isEmpty()) null else offset + pageSize
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }


    override fun getRefreshKey(state: PagingState<Int, PokemonListModel>): Int? {
        return state.anchorPosition?.let { pos ->
            val page = state.closestPageToPosition(pos)
            page?.prevKey?.plus(pageSize) ?: page?.nextKey?.minus(pageSize)
        }
    }
}

