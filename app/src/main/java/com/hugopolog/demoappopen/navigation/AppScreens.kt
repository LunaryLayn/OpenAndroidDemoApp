package com.hugopolog.demoappopen.navigation

import kotlinx.serialization.Serializable


sealed interface AppScreens {

    @Serializable
    data object MainScreen: AppScreens
}