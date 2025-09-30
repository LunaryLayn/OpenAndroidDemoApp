package com.hugopolog.demoappopen.navigation

import kotlinx.serialization.Serializable


sealed interface AppScreens {

    @Serializable
    data object MainScreen: AppScreens
    @Serializable
    data object HomeScreen: AppScreens

    @Serializable
    data class DetailScreen(val pokemonId: Int): AppScreens
}