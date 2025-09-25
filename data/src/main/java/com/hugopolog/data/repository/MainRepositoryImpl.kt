package com.hugopolog.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.hugopolog.data.api.ApiService
import com.hugopolog.data.util.PokemonPagingSource
import com.hugopolog.domain.entities.pokemon.PokemonModel
import com.hugopolog.domain.repository.MainRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MainRepositoryImpl @Inject constructor(
    private val service: ApiService
) : MainRepository {

    companion object{
        const val PAGE_SIZE = 20
        const val PREFETCH_DISTANCE = 3
    }

    override fun getPokemons(): Flow<PagingData<PokemonModel>> {
        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                prefetchDistance = PREFETCH_DISTANCE
            ),
            pagingSourceFactory = { PokemonPagingSource(pageSize = PAGE_SIZE, service = service) }
        ).flow
    }
}