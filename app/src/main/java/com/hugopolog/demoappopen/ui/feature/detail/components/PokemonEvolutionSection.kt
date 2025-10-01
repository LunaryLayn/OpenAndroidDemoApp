/*
 * © 2025 Hugo Polo - Github: https://github.com/LunaryLayn
 */

package com.hugopolog.demoappopen.ui.feature.detail.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.hugopolog.demoappopen.R
import com.hugopolog.demoappopen.ui.components.LottieArrowRight
import com.hugopolog.demoappopen.util.getColor
import com.hugopolog.domain.entities.pokemon.PokemonModel

@Composable
fun EvolutionsTabContent(pokemon: PokemonModel) {
    LazyColumn {
        item {
            Text(
                text = "${stringResource(R.string.evolution_line)} ${pokemon.name}",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(16.dp),
            )
            if (pokemon.evolutions.isNullOrEmpty()) {
                Text(
                    text = stringResource(R.string.no_evolutions),
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            } else {
                EvolutionsCard(pokemon)
            }
        }
    }
}

@Composable
private fun EvolutionsCard(pokemon: PokemonModel) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(16.dp)
    ) {
        pokemon.evolutions!!.sortedBy { it.id }.forEachIndexed { index, evo ->
            Column(
                modifier = Modifier
                    .padding(4.dp)
                    .weight(0.33f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                SubcomposeAsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(evo.sprite)
                        .crossfade(true)
                        .size(120)
                        .diskCachePolicy(CachePolicy.ENABLED)
                        .memoryCachePolicy(CachePolicy.ENABLED)
                        .build(),
                    contentDescription = null,
                    modifier = Modifier.size(120.dp)
                ) {
                    when (painter.state) {
                        is AsyncImagePainter.State.Loading -> {
                            CircularProgressIndicator(
                                strokeWidth = 2.dp,
                                modifier = Modifier.size(32.dp),
                                color = MaterialTheme.colorScheme.primary
                            )
                        }

                        is AsyncImagePainter.State.Error -> {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = stringResource(R.string.error),
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(48.dp)
                            )
                        }

                        else -> {
                            SubcomposeAsyncImageContent()
                        }
                    }
                }

                Text(
                    text = evo.name,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    softWrap = false,
                    overflow = TextOverflow.Visible,
                    modifier = Modifier.wrapContentWidth()
                )

                Text(
                    text = "#${evo.id}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
                )
            }

            if (index < pokemon.evolutions!!.size - 1) {
                LottieArrowRight(
                    color = pokemon.types.first().getColor(),
                    modifier = Modifier.size(40.dp)
                )
            }
        }
    }
}