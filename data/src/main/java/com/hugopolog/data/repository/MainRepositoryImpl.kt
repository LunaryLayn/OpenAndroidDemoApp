package com.hugopolog.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.apollographql.apollo.ApolloClient
import com.hugopolog.data.graphql.GetPokemonDetailQuery
import com.hugopolog.data.mapper.toDto
import com.hugopolog.data.util.PokemonApolloPagingSource
import com.hugopolog.data.util.safeGraphQlCall
import com.hugopolog.domain.entities.config.DataError
import com.hugopolog.domain.entities.config.DataResult
import com.hugopolog.domain.entities.pokemon.PokemonListModel
import com.hugopolog.domain.entities.pokemon.PokemonModel
import com.hugopolog.domain.repository.MainRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MainRepositoryImpl @Inject constructor(
    private val apolloClient: ApolloClient
) : MainRepository {

    companion object {
        const val PAGE_SIZE = 50
        const val PREFETCH_DISTANCE = 3
    }

    override fun getPokemons(name: String, type: List<String>): Flow<PagingData<PokemonListModel>> {
        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                prefetchDistance = PREFETCH_DISTANCE
            ),
            pagingSourceFactory = {
                PokemonApolloPagingSource(apolloClient = apolloClient, pageSize = PAGE_SIZE, name = name, type = type)
            }
        ).flow
    }

    override suspend fun getPokemonDetails(id: Int): DataResult<PokemonModel, DataError> {
        return safeGraphQlCall(
            apiCall = {
                apolloClient.query(GetPokemonDetailQuery(id = id)).execute()
            },
            mapper = { data ->
                val pokemon = data.pokemon.firstOrNull()
                    ?: throw NoSuchElementException("No se encontró un Pokémon con id $id")

                pokemon.toDto() // ← aquí usas tu mapper con ese nombre
            }
        )
    }
}
