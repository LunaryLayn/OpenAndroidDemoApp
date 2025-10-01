/*
 * © 2025 Hugo Polo - Github: https://github.com/LunaryLayn
 */

package com.hugopolog.demoappopen.navigation

import kotlinx.serialization.Serializable


sealed interface AppScreens {

    @Serializable
    data object MainScreen: AppScreens

    @Serializable
    data class DetailScreen(val pokemonId: Int): AppScreens
}