/*
 * © 2025 Hugo Polo - Github: https://github.com/LunaryLayn
 */

package com.hugopolog.demoappopen.ui.feature.main

import androidx.paging.PagingData
import com.hugopolog.domain.entities.pokemon.PokemonListModel
import com.hugopolog.domain.entities.pokemon.PokemonType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

data class MainState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val pokemonList: Flow<PagingData<PokemonListModel>> = emptyFlow(),
    val showTypeDialog: Boolean = false,
    val selectedTypes: List<PokemonType> = emptyList(),
    val searchField : String = "",
)

interface MainActions {
    data class PokemonClicked(val pokemonClicked: PokemonListModel) : MainActions
    data object ShowTypeDialog : MainActions
    data object HideTypeDialog : MainActions
    data class SearchQueryChange(val query: String) : MainActions
    data class ConfirmTypeSelection(val types: List<PokemonType>) : MainActions
}