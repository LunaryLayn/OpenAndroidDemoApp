package com.hugopolog.demoappopen.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.hugopolog.demoappopen.ui.feature.main.MainScreen

@Composable
fun AppNavigation(
    navigator: Navigator = hiltViewModel<NavigationViewModel>().navigator
) {

    val navController = rememberNavController()

    ObserveAsEvents(flow = navigator.navigationActions) { action ->
        when (action) {
            is NavigationAction.Navigate -> {
                Log.d("Navigation", "Navigate to ${action.destination}")
                navController.navigate(action.destination) {
                    action.navOptions(this)
                }
            }

            is NavigationAction.NavigateUp -> navController.navigateUp()
            is NavigationAction.NavigatePop -> navController.navigate(action.destination) {
                popUpTo(navController.graph.id) {
                    inclusive = true
                }
            }
        }
    }

    NavHost(navController = navController, startDestination = navigator.startDestination) {
        composable<AppScreens.MainScreen> {
            MainScreen()
        }
    }
}


