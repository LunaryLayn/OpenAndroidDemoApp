/*
 * © 2025 Hugo Polo - Github: https://github.com/LunaryLayn
 */

package com.hugopolog.demoappopen.ui.feature.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.hugopolog.demoappopen.ui.feature.detail.components.EvolutionsTabContent
import com.hugopolog.demoappopen.ui.feature.detail.components.HeroSection
import com.hugopolog.demoappopen.ui.feature.detail.components.PokemonInfoContent
import com.hugopolog.demoappopen.ui.feature.detail.components.StatsTabContent
import com.hugopolog.demoappopen.util.getColor


@Composable
fun DetailScreen(id: Int) {
    val viewModel = hiltViewModel<DetailViewModel>()
    viewModel.getPokemonDetail(id)
    DetailContent(
        state = viewModel.screenState,
        onAction = viewModel::onAction
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailContent(
    state: DetailState,
    onAction: (DetailActions) -> Unit
) {
    Scaffold(topBar = {
        TopAppBar(
            colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent),
            title = { },
            navigationIcon = {
                IconButton(onClick = { onAction(DetailActions.BackClicked) }) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = null,
                        tint = Color.White
                    )
                }
            }
        )
    }) { paddingValues ->
        if (state.pokemon == null) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                state.pokemon.types.first().getColor().copy(alpha = 0.3f),
                                state.pokemon.types.first().getColor()
                            )
                        )
                    )
                    .padding(top = paddingValues.calculateTopPadding())
            ) {

                HeroSection(state.pokemon)
                Column(
                    modifier = Modifier
                        .clip(RoundedCornerShape(topEnd = 16.dp, topStart = 16.dp))
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.background.copy(alpha = 0.9f))
                        .weight(0.7f)
                ) {
                    val tabs = DetailTab.values()

                    TabRow(
                        selectedTabIndex = tabs.indexOf(state.selectedTab),
                        containerColor = Color.Transparent,
                        contentColor = state.pokemon.types.first().getColor()
                    ) {
                        tabs.forEach { tab ->
                            Tab(
                                selected = state.selectedTab == tab,
                                onClick = { onAction(DetailActions.TabSelected(tab)) },
                                text = { Text(stringResource(tab.titleRes)) }
                            )
                        }
                    }

                    when (state.selectedTab) {
                        DetailTab.Info -> PokemonInfoContent(state.pokemon)
                        DetailTab.Stats -> StatsTabContent(state.pokemon)
                        DetailTab.Evolutions -> EvolutionsTabContent(state.pokemon)
                    }

                }
            }
        }
    }
}





