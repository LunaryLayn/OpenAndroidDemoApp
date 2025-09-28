package com.hugopolog.domain.entities.pokemon

enum class PokemonType {
    Normal,
    Fire,
    Water,
    Electric,
    Grass,
    Ice,
    Fighting,
    Poison,
    Ground,
    Flying,
    Psychic,
    Bug,
    Rock,
    Ghost,
    Dragon,
    Dark,
    Steel,
    Fairy;

    companion object {
        fun fromString(type: String): PokemonType? {
            return values().find { it.name.equals(type, ignoreCase = true) }
        }
    }
}
