package com.hugopolog.demoappopen.ui.feature.main

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.hugopolog.demoappopen.ui.feature.BaseViewModel
import com.hugopolog.domain.entities.config.error.DataResult
import com.hugopolog.domain.usecase.ExampleUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val exampleUseCase: ExampleUseCase
) : BaseViewModel() {
    var screenState by mutableStateOf(MainState())
        private set

    fun onAction(action: MainActions) {
        when (action) {
            is MainActions.DoSomething -> {
                doSomething()
            }
        }
    }

    private fun doSomething() {
        viewModelScope.launch {
            when (val result = exampleUseCase()) {
                is DataResult.Success -> {
                    //Do something with result.data
                }

                is DataResult.Error -> {
                    screenState = screenState.copy(
                        error = "Error: ${result.error}"
                    )
                }
            }
        }
    }
}