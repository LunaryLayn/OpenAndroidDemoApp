package com.hugopolog.demoappopen.ui.feature.main

import android.util.Log
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
        screenState = screenState.copy(isLoading = true)
        getPokemonList()
    }

    fun onAction(action: MainActions) {
        when (action) {
            is MainActions.PokemonClicked -> {
                getPokemonList()
            }
            is MainActions.ShowTypeDialog -> {
                screenState = screenState.copy(showTypeDialog = true)
            }
            is MainActions.HideTypeDialog -> {
                screenState = screenState.copy(showTypeDialog = false)
            }
            is MainActions.ConfirmTypeSelection -> {
                screenState = screenState.copy(
                    showTypeDialog = false,
                    selectedTypes = action.types
                )
                // Aquí podrías lanzar un nuevo use case filtrado
                // getPokemonListFiltered(action.types)
            }
        }
    }

    private fun getPokemonList() {
        val pokemon = getPokemonListUseCase().cachedIn(viewModelScope)
        screenState = screenState.copy(
            pokemonList = pokemon,
            isLoading = false,
            error = null
        )
    }
}