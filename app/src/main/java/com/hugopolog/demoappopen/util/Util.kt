/*
 * © 2025 Hugo Polo - Github: https://github.com/LunaryLayn
 */

package com.hugopolog.demoappopen.util

import androidx.compose.ui.graphics.Color
import com.hugopolog.demoappopen.R
import com.hugopolog.demoappopen.ui.theme.BugColor
import com.hugopolog.demoappopen.ui.theme.DarkColor
import com.hugopolog.demoappopen.ui.theme.DragonColor
import com.hugopolog.demoappopen.ui.theme.ElectricColor
import com.hugopolog.demoappopen.ui.theme.FairyColor
import com.hugopolog.demoappopen.ui.theme.FightingColor
import com.hugopolog.demoappopen.ui.theme.FireColor
import com.hugopolog.demoappopen.ui.theme.FlyingColor
import com.hugopolog.demoappopen.ui.theme.GhostColor
import com.hugopolog.demoappopen.ui.theme.GrassColor
import com.hugopolog.demoappopen.ui.theme.GroundColor
import com.hugopolog.demoappopen.ui.theme.IceColor
import com.hugopolog.demoappopen.ui.theme.NormalColor
import com.hugopolog.demoappopen.ui.theme.PoisonColor
import com.hugopolog.demoappopen.ui.theme.PsychicColor
import com.hugopolog.demoappopen.ui.theme.RockColor
import com.hugopolog.demoappopen.ui.theme.SteelColor
import com.hugopolog.demoappopen.ui.theme.WaterColor
import com.hugopolog.domain.entities.pokemon.PokemonType


fun PokemonType.getColor(): Color {
    return when (this) {
        PokemonType.Normal -> NormalColor
        PokemonType.Fire -> FireColor
        PokemonType.Water -> WaterColor
        PokemonType.Electric -> ElectricColor
        PokemonType.Grass -> GrassColor
        PokemonType.Ice -> IceColor
        PokemonType.Fighting -> FightingColor
        PokemonType.Poison -> PoisonColor
        PokemonType.Ground -> GroundColor
        PokemonType.Flying -> FlyingColor
        PokemonType.Psychic -> PsychicColor
        PokemonType.Bug -> BugColor
        PokemonType.Rock -> RockColor
        PokemonType.Ghost -> GhostColor
        PokemonType.Dragon -> DragonColor
        PokemonType.Dark -> DarkColor
        PokemonType.Steel -> SteelColor
        PokemonType.Fairy -> FairyColor
    }
}

fun PokemonType.getImage(): Int {
    return when (this) {
        PokemonType.Normal -> R.drawable.normal
        PokemonType.Fire -> R.drawable.fire
        PokemonType.Water -> R.drawable.water
        PokemonType.Electric -> R.drawable.electric
        PokemonType.Grass -> R.drawable.grass
        PokemonType.Ice -> R.drawable.ice
        PokemonType.Fighting -> R.drawable.fighting
        PokemonType.Poison -> R.drawable.poison
        PokemonType.Ground -> R.drawable.ground
        PokemonType.Flying -> R.drawable.flying
        PokemonType.Psychic -> R.drawable.psychic
        PokemonType.Bug -> R.drawable.bug
        PokemonType.Rock -> R.drawable.rock
        PokemonType.Ghost -> R.drawable.ghost
        PokemonType.Dragon -> R.drawable.dragon
        PokemonType.Dark -> R.drawable.dark
        PokemonType.Steel -> R.drawable.steel
        PokemonType.Fairy -> R.drawable.fairy
    }
}