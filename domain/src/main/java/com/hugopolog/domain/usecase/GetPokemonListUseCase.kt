/*
 * © 2025 Hugo Polo - Github: https://github.com/LunaryLayn
 */

package com.hugopolog.domain.usecase

import com.hugopolog.domain.entities.pokemon.PokemonType
import com.hugopolog.domain.repository.MainRepository
import javax.inject.Inject

class GetPokemonListUseCase @Inject constructor(
    private val repository: MainRepository
) {
    operator fun invoke(
        name: String? = null,
        type: List<PokemonType> = emptyList()
    ) = repository.getPokemons(
        name = if (name.isNullOrBlank()) "%" else "%$name%",
        type = if (type.isNotEmpty()) type.map { it.name.lowercase() } else PokemonType.entries.map { it.name.lowercase() },
    )
}