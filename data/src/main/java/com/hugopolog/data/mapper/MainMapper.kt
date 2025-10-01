/*
 * © 2025 Hugo Polo - Github: https://github.com/LunaryLayn
 */

package com.hugopolog.data.mapper

import com.hugopolog.data.graphql.GetAllPokemonQuery
import com.hugopolog.data.graphql.GetPokemonDetailQuery
import com.hugopolog.domain.entities.pokemon.Evolution
import com.hugopolog.domain.entities.pokemon.GenderDistribution
import com.hugopolog.domain.entities.pokemon.PokemonListModel
import com.hugopolog.domain.entities.pokemon.PokemonModel
import com.hugopolog.domain.entities.pokemon.PokemonType

fun GetAllPokemonQuery.Pokemon.toDto(): PokemonListModel {
    return PokemonListModel(
        id = this.id,
        name = this.name,
        sprite =  buildSpriteUrl(this.id),
        type = this.pokemontypes.mapNotNull { PokemonType.fromString(it.type!!.name) }
    )
}

fun GetPokemonDetailQuery.Pokemon.toDto(): PokemonModel {
    val species = this.pokemonspecy

    return PokemonModel(
        id = this.id,
        name = this.name.replaceFirstChar { it.uppercase() },
        height = (this.height ?: 0)/ 10f,
        weight =( this.weight ?: 0)/ 10f,
        gender = parseGenderRate(species?.gender_rate ?: 0),
        baseExperience = this.base_experience ?: 0,
        abilities = this.pokemonabilities.map { abilityWrapper ->
            abilityWrapper.ability?.name?.replaceFirstChar { it.uppercase() } ?: ""
        },
        types = this.pokemontypes.mapNotNull { PokemonType.fromString(it.type!!.name) },
        stats = this.pokemonstats.associate { statWrapper ->
            val rawName = statWrapper.stat!!.name
            val displayName = when (rawName.lowercase()) {
                "hp" -> "HP"
                "attack" -> "Attack"
                "defense" -> "Defense"
                "special-attack" -> "Sp. Attack"
                "special-defense" -> "Sp. Defense"
                "speed" -> "Speed"
                else -> rawName.replaceFirstChar { it.uppercase() }
            }
            displayName to statWrapper.base_stat
        },
        evolutions = species?.evolutionchain?.pokemonspecies?.map { evo ->
            Evolution(
                id = evo.id,
                name = evo.name.replaceFirstChar { it.uppercase() },
                sprite = buildSpriteUrl(evo.id)
            )
        },
        generation = formatGeneration(species?.generation?.name!!),
        sprite = buildSpriteUrl(this.id)
    )
}

private fun buildSpriteUrl(id: Int): String =
    "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/$id.png"

fun parseGenderRate(rate: Int): GenderDistribution {
    return if (rate == -1) {
        GenderDistribution.Genderless
    } else {
        val female = (rate * 100) / 8
        val male = 100 - female
        GenderDistribution.WithGender(
            malePercent = male,
            femalePercent = female
        )
    }
}

fun formatGeneration(raw: String): String {
    return raw.replace("generation-", "Generation ")
        .replaceFirstChar { it.uppercase() } // asegura la G mayúscula
        .substringBefore(" ") + " " +
            raw.substringAfter("-").uppercase()
}
