/*
 * © 2025 Hugo Polo - Github: https://github.com/LunaryLayn
 */

package com.hugopolog.demoappopen.ui.feature.main

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.hugopolog.demoappopen.navigation.AppScreens
import com.hugopolog.demoappopen.ui.feature.BaseViewModel
import com.hugopolog.domain.usecase.GetPokemonListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
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
                navigate(AppScreens.DetailScreen(action.pokemonClicked.id))
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
                getPokemonList()
            }

            is MainActions.SearchQueryChange -> {
                screenState = screenState.copy(
                    searchField = action.query
                )
                getPokemonList()
            }
        }
    }

    private fun getPokemonList() {
        val pokemon = getPokemonListUseCase(name = screenState.searchField, type = screenState.selectedTypes).cachedIn(viewModelScope)
        screenState = screenState.copy(
            pokemonList = pokemon,
            isLoading = false,
            error = null
        )
    }
}