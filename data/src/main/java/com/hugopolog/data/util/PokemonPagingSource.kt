package com.hugopolog.data.util

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.apollographql.apollo.ApolloClient
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.hugopolog.data.entities.pokemon.PokemonDataModel
import com.hugopolog.data.graphql.GetAllPokemonQuery
import com.hugopolog.data.mapper.toDto
import com.hugopolog.domain.entities.config.DataError
import com.hugopolog.domain.entities.pokemon.PokemonModel
import org.json.JSONObject

class PokemonApolloPagingSource(
    private val apolloClient: ApolloClient,
    private val pageSize: Int
) : PagingSource<Int, PokemonModel>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PokemonModel> {
        val offset = params.key ?: 0

        return try {
            val response = apolloClient.query(
                GetAllPokemonQuery(limit = pageSize, offset = offset)
            ).execute()

            val pokemons = response.data?.pokemons?.map { p ->

                PokemonDataModel(
                    id = p.id,
                    name = p.name,
                    type = p.pokemontypes.map { it.type?.name ?: "" },
                ).toDto()
            } ?: emptyList()

            LoadResult.Page(
                data = pokemons,
                prevKey = if (offset == 0) null else offset - pageSize,
                nextKey = if (pokemons.isEmpty()) null else offset + pageSize
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }


    override fun getRefreshKey(state: PagingState<Int, PokemonModel>): Int? {
        return state.anchorPosition?.let { pos ->
            val page = state.closestPageToPosition(pos)
            page?.prevKey?.plus(pageSize) ?: page?.nextKey?.minus(pageSize)
        }
    }
}


class AppException(val dataError: DataError) : Exception(dataError.toString())