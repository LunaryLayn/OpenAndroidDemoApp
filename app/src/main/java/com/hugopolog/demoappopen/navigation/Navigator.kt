package com.hugopolog.demoappopen.navigation

import androidx.navigation.NavOptionsBuilder
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject

interface Navigator {
    val startDestination: AppScreens
    val navigationActions: Flow<NavigationAction>

    suspend fun navigate(
        destination: AppScreens,
        navOptions: NavOptionsBuilder.() -> Unit = {},
    )

    suspend fun navigateUp()
    suspend fun navigatePop(destination: AppScreens)
}

class DefaultNavigator @Inject constructor(
    override val startDestination: AppScreens
) : Navigator {

    private val _navigationActions = Channel<NavigationAction>()
    override val navigationActions = _navigationActions.receiveAsFlow()

    override suspend fun navigate(
        destination: AppScreens,
        navOptions: NavOptionsBuilder.() -> Unit,
    ) {
        _navigationActions.send(NavigationAction.Navigate(destination, navOptions))
    }

    override suspend fun navigateUp() {
        _navigationActions.send(NavigationAction.NavigateUp)
    }

    override suspend fun navigatePop(destination: AppScreens) {
        _navigationActions.send(NavigationAction.NavigatePop(destination))
    }
}