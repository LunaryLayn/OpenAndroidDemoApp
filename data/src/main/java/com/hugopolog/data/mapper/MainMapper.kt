package com.hugopolog.data.mapper

import com.hugopolog.data.entities.pokemon.PokemonDataModel
import com.hugopolog.domain.entities.pokemon.PokemonModel

fun PokemonDataModel.toDto(): PokemonModel {
    val id = extractIdFromUrl(url)
    return PokemonModel(
        id = id,
        name = this.name,
        spriteUrl = spriteUrl(id)
    )
}

fun extractIdFromUrl(url: String): Int {
    return url.trimEnd('/').substringAfterLast('/').toInt()
}

// Using this to get the sprite image of the pokemon on the list call.
fun spriteUrl(id: Int): String {
    return "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/$id.png"
}