/*
 * © 2025 Hugo Polo - Github: https://github.com/LunaryLayn
 */

package com.hugopolog.demoappopen.ui.feature

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavOptionsBuilder
import com.hugopolog.demoappopen.navigation.AppScreens
import com.hugopolog.demoappopen.navigation.Navigator
import kotlinx.coroutines.launch
import javax.inject.Inject

abstract class BaseViewModel() : ViewModel() {

    @Inject
    lateinit var navigator: Navigator


    fun navigate(route: AppScreens, navOptions: NavOptionsBuilder.() -> Unit = {}) {
        viewModelScope.launch {
            navigator.navigate(route, navOptions)
        }
    }

    fun navigateUp() {
        viewModelScope.launch {
            navigator.navigateUp()
        }
    }

    fun navigatePop(route: AppScreens) {
        viewModelScope.launch {
            navigator.navigatePop(route)
        }
    }
}