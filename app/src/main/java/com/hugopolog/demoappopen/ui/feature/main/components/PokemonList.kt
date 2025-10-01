package com.hugopolog.demoappopen.ui.feature.main.components

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.hugopolog.demoappopen.R
import com.hugopolog.demoappopen.util.getColor
import com.hugopolog.demoappopen.util.getImage
import com.hugopolog.domain.entities.pokemon.PokemonListModel


@Composable
fun PokemonsList(
    pokemons: LazyPagingItems<PokemonListModel>,
    modifier: Modifier = Modifier,
    onPokemonClicked: (PokemonListModel) -> Unit
) {
    Box(modifier = modifier.fillMaxSize()) {

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(
                count = pokemons.itemCount,
                key = { index -> pokemons[index]?.id ?: index }
            ) { index ->
                pokemons[index]?.let { pokemon ->
                    PokemonItem(pokemon = pokemon) {
                        onPokemonClicked(it)
                    }
                }
            }

            if (pokemons.loadState.append is LoadState.Loading) {
                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        androidx.compose.material3.LinearProgressIndicator(
                            modifier = Modifier.fillMaxWidth(0.6f)
                        )
                    }
                }
            }
        }

        if (pokemons.loadState.refresh is LoadState.Loading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}

@Composable
fun PokemonItem(pokemon: PokemonListModel, onPokemonClicked: (PokemonListModel) -> Unit) {
    Log.d("PokemonItem", "Pokemon: $pokemon")
    Row(
        modifier = Modifier
            .clickable { onPokemonClicked(pokemon) }
            .clip(RoundedCornerShape(16.dp))
            .fillMaxWidth()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        pokemon.type.first().getColor().copy(alpha = 0.7f),
                        pokemon.type.first().getColor()
                    )
                )
            )
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Text(text = "#${pokemon.id}", fontWeight = FontWeight.SemiBold)
            Text(
                text = pokemon.name.replaceFirstChar { it.uppercase() },
                fontWeight = FontWeight.ExtraBold,
                color = Color.White,
                style = MaterialTheme.typography.headlineMedium
            )
            Row {
                pokemon.type.forEach { tipo ->
                    Image(
                        painter = painterResource(id = tipo.getImage()),
                        contentDescription = tipo.name,
                        modifier = Modifier
                            .padding(end = 4.dp)
                            .size(40.dp)
                    )
                }
            }
        }
        Spacer(modifier = Modifier.weight(1f))

        Box(
            modifier = Modifier.size(120.dp),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.pokeball),
                contentDescription = stringResource(R.string.pokeball),
                modifier = Modifier.fillMaxSize(),
                colorFilter = ColorFilter.tint(Color.White.copy(alpha = 0.2f))
            )
            SubcomposeAsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(pokemon.sprite)
                    .crossfade(true)
                    .size(160)
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
                            color = Color.White
                        )
                    }

                    is AsyncImagePainter.State.Error -> {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = stringResource(R.string.error),
                            tint = Color.White,
                            modifier = Modifier.size(48.dp)
                        )
                    }

                    else -> {
                        SubcomposeAsyncImageContent()
                    }
                }
            }
        }
    }
}