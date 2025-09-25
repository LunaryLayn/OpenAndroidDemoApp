package com.hugopolog.domain.repository

import androidx.paging.PagingData
import com.hugopolog.domain.entities.config.DataError
import com.hugopolog.domain.entities.config.DataResult
import com.hugopolog.domain.entities.pokemon.PokemonModel
import kotlinx.coroutines.flow.Flow

interface MainRepository {
     fun getPokemons(): Flow<PagingData<PokemonModel>>
}