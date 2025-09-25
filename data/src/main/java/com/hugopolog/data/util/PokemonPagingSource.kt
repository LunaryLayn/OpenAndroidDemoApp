package com.hugopolog.data.util

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.hugopolog.data.api.ApiService
import com.hugopolog.data.mapper.toDto
import com.hugopolog.domain.entities.config.DataError
import com.hugopolog.domain.entities.config.DataResult
import com.hugopolog.domain.entities.pokemon.PokemonModel

class PokemonPagingSource(
    private val service: ApiService,
    private val pageSize: Int
) : PagingSource<Int, PokemonModel>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PokemonModel> {
        val offset = params.key ?: 0

        return when (val result = ApiHelper.safeApiCall(
            apiCall = { service.getPokemons(limit = pageSize, offset = offset) },
            mapper = { response ->
                response.results.map { pokemon ->
                    pokemon.toDto()
                }
            }
        )) {
            is DataResult.Success -> {
                LoadResult.Page(
                    data = result.data,
                    prevKey = if (offset == 0) null else offset - pageSize,
                    nextKey = if (result.data.isEmpty()) null else offset + pageSize
                )
            }

            is DataResult.Error -> {
                LoadResult.Error(AppException(result.error))
            }
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