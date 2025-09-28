package com.hugopolog.demoappopen.ui.feature.main

import androidx.paging.PagingData
import com.hugopolog.domain.entities.pokemon.PokemonModel
import com.hugopolog.domain.entities.pokemon.PokemonType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

data class MainState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val pokemonList: Flow<PagingData<PokemonModel>> = emptyFlow(),
    val showTypeDialog: Boolean = false,
    val selectedTypes: List<PokemonType> = emptyList()
)

interface MainActions {
    data object PokemonClicked : MainActions
    data object ShowTypeDialog : MainActions
    data object HideTypeDialog : MainActions
    data class ConfirmTypeSelection(val types: List<PokemonType>) : MainActions
}