package com.hugopolog.demoappopen.ui.feature.detail

import androidx.annotation.StringRes
import com.hugopolog.demoappopen.R
import com.hugopolog.domain.entities.pokemon.PokemonModel

data class DetailState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val pokemon: PokemonModel? = null,
    val selectedTab: DetailTab = DetailTab.Info
)

interface DetailActions {
    data object BackClicked : DetailActions
    data class TabSelected(val tab: DetailTab) : DetailActions
}

enum class DetailTab(@StringRes val titleRes: Int) {
    Info(R.string.info_tab),
    Stats(R.string.stats_tab),
    Evolutions(R.string.evolutions_tab)
}