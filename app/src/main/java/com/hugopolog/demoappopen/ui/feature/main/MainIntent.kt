package com.hugopolog.demoappopen.ui.feature.main

data class MainState(
    val isLoading: Boolean = false,
    val error: String? = null
)

interface MainActions {
    data object DoSomething : MainActions
}