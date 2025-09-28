package com.hugopolog.data.mapper

import com.hugopolog.data.entities.pokemon.PokemonDataModel
import com.hugopolog.domain.entities.pokemon.PokemonModel
import com.hugopolog.domain.entities.pokemon.PokemonType

fun PokemonDataModel.toDto(): PokemonModel {
    return PokemonModel(
        id = this.id,
        name = this.name,
        sprite = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/${this.id}.png",
        type = this.type.mapNotNull { PokemonType.fromString(it) }
    )
}