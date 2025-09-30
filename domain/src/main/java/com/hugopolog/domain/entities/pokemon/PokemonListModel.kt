package com.hugopolog.domain.entities.pokemon

data class PokemonListModel(
    val id: Int,
    val name: String,
    val sprite: String,
    val type: List<PokemonType>,
)
