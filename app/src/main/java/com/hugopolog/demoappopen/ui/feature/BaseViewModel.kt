package com.hugopolog.demoappopen.ui.feature

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavOptionsBuilder
import com.hugopolog.demoappopen.navigation.AppScreens
import com.hugopolog.demoappopen.navigation.Navigator
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.util.logging.ErrorManager
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