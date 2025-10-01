/*
 * © 2025 Hugo Polo - Github: https://github.com/LunaryLayn
 */

package com.hugopolog.domain.entities.pokemon

data class PokemonModel(
    val id: Int,
    val name: String,
    val height: Float,
    val weight: Float,
    val gender: GenderDistribution,
    val baseExperience: Int,
    val abilities: List<String>,
    val types: List<PokemonType>,
    val stats: Map<String, Int>,
    val evolutions: List<Evolution>?,
    val generation: String,
    val sprite: String
)

data class Evolution(
    val id: Int,
    val name: String,
    val sprite: String
)

sealed class GenderDistribution {
    data class WithGender(
        val malePercent: Int,
        val femalePercent: Int
    ) : GenderDistribution()

    object Genderless : GenderDistribution()
}