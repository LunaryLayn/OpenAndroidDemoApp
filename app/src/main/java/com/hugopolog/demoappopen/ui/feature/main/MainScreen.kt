package com.hugopolog.demoappopen.ui.feature.main

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun MainScreen() {
    val viewModel = hiltViewModel<MainViewModel>()
    MainScreenContent(
        state = viewModel.screenState,
        onAction = viewModel::onAction
    )
}

@Composable
fun MainScreenContent(
    state: MainState,
    onAction: (MainActions) -> Unit) {

}