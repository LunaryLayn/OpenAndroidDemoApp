package com.hugopolog.demoappopen.ui.feature.main

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.hugopolog.domain.entities.pokemon.PokemonModel

@Composable
fun MainScreen(viewModel: MainViewModel = hiltViewModel()) {
    MainScreenContent(
        state = viewModel.screenState,
        onAction = viewModel::onAction
    )
}

@Composable
fun MainScreenContent(
    state: MainState,
    onAction: (MainActions) -> Unit
) {
    val pokemons = state.pokemonList.collectAsLazyPagingItems()

    PokemonsList(pokemons)
}

@Composable
fun PokemonsList(pokemons: LazyPagingItems<PokemonModel>) {
    LazyColumn {
        items(pokemons.itemCount) { index ->
            pokemons[index]?.let { pokemon ->
                PokemonItem(pokemon = pokemon)
            }
        }
    }
}

@Composable
fun PokemonItem(pokemon: PokemonModel) {
    Row() {
        Text(text = pokemon.name)
    }
}