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
        height = this.height ?: 0,
        weight = this.weight ?: 0,
        gender = parseGenderRate(species?.gender_rate ?: 0),
        baseExperience = this.base_experience ?: 0,
        abilities = this.pokemonabilities.map { abilityWrapper ->
            abilityWrapper.ability?.name?.replaceFirstChar { it.uppercase() } ?: ""
        },
        types = this.pokemontypes.mapNotNull { PokemonType.fromString(it.type!!.name) },
        stats = this.pokemonstats.associate { statWrapper ->
            statWrapper.stat!!.name.replaceFirstChar { it.uppercase() } to statWrapper.base_stat
        },
        evolutions = species?.evolutionchain?.pokemonspecies?.map { evo ->
            Evolution(
                id = evo.id,
                name = evo.name.replaceFirstChar { it.uppercase() },
                sprite = buildSpriteUrl(evo.id)
            )
        },
        generation = species?.generation?.name!!.replaceFirstChar { it.uppercase() },
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