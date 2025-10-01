/*
 * © 2025 Hugo Polo - Github: https://github.com/LunaryLayn
 */

package com.hugopolog.demoappopen.ui.feature.main

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import com.hugopolog.demoappopen.R
import com.hugopolog.demoappopen.ui.components.TypeSelectorDialog
import com.hugopolog.demoappopen.ui.feature.main.components.PokemonsList

@Composable
fun MainScreen(viewModel: MainViewModel = hiltViewModel()) {
    MainScreenContent(
        state = viewModel.screenState,
        onAction = viewModel::onAction
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreenContent(
    state: MainState,
    onAction: (MainActions) -> Unit
) {
    val pokemons = state.pokemonList.collectAsLazyPagingItems()
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { onAction(MainActions.ShowTypeDialog) }) {
                Icon(
                    imageVector = Icons.Default.Settings,
                    contentDescription = stringResource(R.string.filter)
                )
            }
        }) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            if (state.showTypeDialog) {
                TypeSelectorDialog(
                    selectedTypes = state.selectedTypes,
                    onDismiss = { onAction(MainActions.HideTypeDialog) },
                    onConfirm = { selected ->
                        onAction(MainActions.ConfirmTypeSelection(selected))
                    }
                )
            }

            Column(modifier = Modifier.padding(top = 32.dp, end = 32.dp, start = 32.dp, bottom = 16.dp)) {
                Text(
                    stringResource(R.string.pokedex_title),
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Bold
                )
                Spacer(Modifier.padding(4.dp))
                Text(
                    stringResource(R.string.pokedex_description),
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(Modifier.padding(4.dp))
                OutlinedTextField(
                    state.searchField,
                    { onAction(MainActions.SearchQueryChange(it)) },
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = { Text(stringResource(R.string.search_placeholder)) },
                    leadingIcon = {Icon(imageVector = Icons.Default.Search, contentDescription = "")}
                )
                Spacer(Modifier.padding(8.dp))
                PokemonsList(pokemons) {
                    onAction(MainActions.PokemonClicked(it))
                }
            }
        }
    }
}


