package com.hugopolog.demoappopen.ui.feature.main

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.hugopolog.demoappopen.ui.feature.BaseViewModel
import com.hugopolog.domain.entities.config.DataResult
import com.hugopolog.domain.repository.MainRepository
import com.hugopolog.domain.usecase.GetPokemonListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getPokemonListUseCase: GetPokemonListUseCase,
) : BaseViewModel() {

    var screenState by mutableStateOf(MainState())
        private set

    init {
        getPokemonList()
    }

    fun onAction(action: MainActions) {
        when (action) {
            is MainActions.PokemonClicked -> {
                getPokemonList()
            }
        }
    }

    private fun getPokemonList() {
        screenState = screenState.copy(
            pokemonList = getPokemonListUseCase().cachedIn(viewModelScope),
            isLoading = false,
            error = null
        )
    }
}