package com.hugopolog.demoappopen.ui.feature.detail.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.hugopolog.demoappopen.R
import com.hugopolog.demoappopen.ui.components.LottiePokeball
import com.hugopolog.demoappopen.util.getImage
import com.hugopolog.domain.entities.pokemon.PokemonModel


@Composable
fun ColumnScope.HeroSection(pokemon: PokemonModel) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .weight(0.4f)
            .padding(16.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.pokeball),
            contentDescription = stringResource(R.string.pokeball),
            modifier = Modifier.align(Alignment.BottomCenter),
            colorFilter = ColorFilter.tint(Color.White.copy(alpha = 0.1f))
        )
        Column {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = pokemon.name,
                    color = Color.White,
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.ExtraBold
                )
                LottiePokeball()
                Spacer(modifier = Modifier.weight(1f))

                Text(
                    text = "#${pokemon.id}",
                    color = Color.White,
                    style = MaterialTheme.typography.headlineSmall
                )
            }
            Row {
                pokemon.types.forEach { type ->
                    Image(
                        painter = painterResource(id = type.getImage()),
                        contentDescription = type.name,
                        modifier = Modifier
                            .padding(end = 4.dp)
                            .size(40.dp)
                    )
                }
            }
            SubcomposeAsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(pokemon.sprite)
                    .crossfade(true)
                    .size(240)
                    .diskCachePolicy(CachePolicy.ENABLED)
                    .memoryCachePolicy(CachePolicy.ENABLED)
                    .build(),
                contentDescription = null,
                modifier = Modifier
                    .size(240.dp)
                    .align(Alignment.CenterHorizontally)
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