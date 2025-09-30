package com.hugopolog.domain.repository

import androidx.paging.PagingData
import com.hugopolog.domain.entities.config.DataError
import com.hugopolog.domain.entities.config.DataResult
import com.hugopolog.domain.entities.pokemon.PokemonListModel
import com.hugopolog.domain.entities.pokemon.PokemonModel
import kotlinx.coroutines.flow.Flow

interface MainRepository {
     fun getPokemons(name: String, type: List<String>): Flow<PagingData<PokemonListModel>>
     suspend fun getPokemonDetails(id: Int): DataResult<PokemonModel, DataError>
}