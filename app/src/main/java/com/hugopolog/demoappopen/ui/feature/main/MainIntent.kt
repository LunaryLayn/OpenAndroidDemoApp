package com.hugopolog.demoappopen.ui.feature.main

import androidx.paging.PagingData
import com.hugopolog.domain.entities.pokemon.PokemonModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

data class MainState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val pokemonList: Flow<PagingData<PokemonModel>> = emptyFlow()
)

interface MainActions {
    data object PokemonClicked : MainActions
}