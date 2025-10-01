/*
 * © 2025 Hugo Polo - Github: https://github.com/LunaryLayn
 */

package com.hugopolog.demoappopen.navigation

import androidx.navigation.NavOptionsBuilder

sealed interface NavigationAction {
    data class Navigate(
        val destination: AppScreens,
        val navOptions: NavOptionsBuilder.() -> Unit = {},
    ) : NavigationAction

    data object NavigateUp : NavigationAction
    data class NavigatePop(val destination: AppScreens) : NavigationAction
}