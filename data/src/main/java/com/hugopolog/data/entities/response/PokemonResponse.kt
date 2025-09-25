package com.hugopolog.data.entities.response

import com.hugopolog.data.entities.pokemon.PokemonDataModel

data class PokemonResponse(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<PokemonDataModel>
)