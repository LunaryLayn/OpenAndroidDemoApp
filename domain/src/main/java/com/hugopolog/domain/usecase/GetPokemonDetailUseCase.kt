package com.hugopolog.domain.usecase

import com.hugopolog.domain.entities.config.DataError
import com.hugopolog.domain.entities.config.DataResult
import com.hugopolog.domain.entities.pokemon.PokemonModel
import com.hugopolog.domain.repository.MainRepository
import javax.inject.Inject

class GetPokemonDetailUseCase @Inject constructor(
    private val repository: MainRepository
) {
    suspend operator fun invoke(id: Int): DataResult<PokemonModel, DataError> {
        return repository.getPokemonDetails(id)
    }
}