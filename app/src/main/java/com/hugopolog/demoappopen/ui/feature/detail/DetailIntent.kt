package com.hugopolog.demoappopen.ui.feature.detail

import androidx.paging.PagingData
import com.hugopolog.demoappopen.ui.feature.main.MainActions
import com.hugopolog.domain.entities.pokemon.PokemonListModel
import com.hugopolog.domain.entities.pokemon.PokemonModel
import com.hugopolog.domain.entities.pokemon.PokemonType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

data class DetailState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val pokemon: PokemonModel? = null,
)

interface DetailActions {
    data object BackClicked : DetailActions
}