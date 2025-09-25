package com.hugopolog.domain.usecase

import com.hugopolog.domain.repository.MainRepository
import javax.inject.Inject

class GetPokemonListUseCase @Inject constructor(private val repository: MainRepository) {
    operator fun invoke() = repository.getPokemons()
}
