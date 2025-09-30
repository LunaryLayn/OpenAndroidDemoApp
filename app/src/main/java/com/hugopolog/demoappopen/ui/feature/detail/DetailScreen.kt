package com.hugopolog.demoappopen.ui.feature.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.core.graphics.BlendModeColorFilterCompat
import androidx.core.graphics.BlendModeCompat
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.airbnb.lottie.LottieProperty
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.LottieDynamicProperty
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.airbnb.lottie.compose.rememberLottieDynamicProperties
import com.airbnb.lottie.compose.rememberLottieDynamicProperty
import com.hugopolog.demoappopen.R
import com.hugopolog.demoappopen.util.getColor
import com.hugopolog.demoappopen.util.getImage
import com.hugopolog.domain.entities.pokemon.Evolution
import com.hugopolog.domain.entities.pokemon.PokemonModel

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
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "", tint = Color.White)
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
                                state.pokemon.types.first().getColor().copy(alpha = 0.3f), // más transparente arriba
                                state.pokemon.types.first().getColor()  // más intenso abajo
                            )
                        )
                    )
                    .padding(top = paddingValues.calculateTopPadding())
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(0.4f)
                        .padding(16.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.pokeball),
                        contentDescription = "Pokeball",
                        modifier = Modifier.align(Alignment.BottomCenter),
                        colorFilter = ColorFilter.tint(Color.White.copy(alpha = 0.1f))
                    )
                    Column() {

                        val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.pokeball))
                        val progress by animateLottieCompositionAsState(
                            composition,
                            iterations = LottieConstants.IterateForever,
                            isPlaying = true,
                            speed = 0.8f
                        )

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = state.pokemon.name,
                                color = Color.White,
                                style = MaterialTheme.typography.headlineLarge,
                                fontWeight = FontWeight.ExtraBold
                            )
                            LottieAnimation(
                                composition = composition,
                                progress = { progress },
                                modifier = Modifier
                                    .size(48.dp)
                                    .padding(8.dp)
                            )
                            Spacer(modifier = Modifier.weight(1f))
                            Text(text = "#${state.pokemon.id}", color = Color.White, style = MaterialTheme.typography.headlineSmall)
                        }
                        Row {
                            state.pokemon.types.forEach { type ->
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
                                .data(state.pokemon.sprite)
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
                                        contentDescription = "Error",
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
                LazyColumn(
                    modifier = Modifier

                        .clip(RoundedCornerShape(topEnd = 16.dp, topStart = 16.dp))
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.background.copy(alpha = 0.9f))
                        .weight(0.7f)
                ) {
                    item {
                        Text(
                            text = "About",
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(16.dp),
                        )
                    }
                    item {
                        Text(
                            text = "Evolution",
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(16.dp),
                        )
                        if(state.pokemon.evolutions.isNullOrEmpty()) {
                            Text(
                                text = "This pokemon has no evolutions",
                                style = MaterialTheme.typography.bodyLarge,
                                modifier = Modifier.padding(horizontal = 16.dp)
                            )
                        } else {
                            EvolutionsCard(state.pokemon)
                        }
                    }
                }
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
                                    contentDescription = "Error",
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
                        modifier = Modifier
                            .size(40.dp)
                    )
                }
        }
    }
}


@Composable
fun LottieArrowRight(
    color: Color,
    modifier: Modifier = Modifier
) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.arrow))
    val progress by animateLottieCompositionAsState(
        composition,
        iterations = LottieConstants.IterateForever,
        speed = 2f
    )

    val dynamicProperties = rememberLottieDynamicProperties(
        rememberLottieDynamicProperty(
            property = LottieProperty.COLOR_FILTER,
            value = BlendModeColorFilterCompat.createBlendModeColorFilterCompat(
                color.toArgb(),
                BlendModeCompat.SRC_ATOP
            ),
            keyPath = arrayOf(
                "**"
            )
        )
    )

    LottieAnimation(
        composition = composition,
        progress = { progress },
        modifier = modifier,
        dynamicProperties = dynamicProperties
    )
}
