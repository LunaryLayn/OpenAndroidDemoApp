package com.hugopolog.demoappopen.ui.feature.main

import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.hugopolog.demoappopen.R
import com.hugopolog.demoappopen.ui.components.TypeSelectorDialog
import com.hugopolog.demoappopen.util.getColor
import com.hugopolog.demoappopen.util.getImage
import com.hugopolog.domain.entities.pokemon.PokemonModel

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
    Log.d("MainScreen", "Pokemons: ${pokemons.itemCount}")
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.bulbasaur))
    val progress by animateLottieCompositionAsState(
        composition,
        iterations = LottieConstants.IterateForever,
        isPlaying = true,
        speed = 0.8f
    )
    val composition2 by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.pokeball))
    val progress2 by animateLottieCompositionAsState(
        composition2,
        iterations = LottieConstants.IterateForever,
        isPlaying = true,
        speed = 0.5f
    )
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { onAction(MainActions.ShowTypeDialog) }) {
                Icon(imageVector = Icons.Default.Settings, contentDescription = "Filtro")
            }
        },
        topBar = {
        TopAppBar(
            title = {}, // vacío
            actions = {
                IconButton(onClick = { /* TODO: Acción 1 */ }) {
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = "Favorito"
                    )
                }
                IconButton(onClick = { /* TODO: Acción 2 */ }) {
                    Icon(
                        imageVector = Icons.Default.Build,
                        contentDescription = "Ajustes"
                    )
                }
            }
        )
    }) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {

            if (state.showTypeDialog) {
                TypeSelectorDialog(
                    onDismiss = { onAction(MainActions.HideTypeDialog) },
                    onConfirm = { selected ->
                        onAction(MainActions.ConfirmTypeSelection(selected))
                    }
                )
            }


            Column(modifier = Modifier.padding(vertical = 16.dp, horizontal = 32.dp)) {
                Text("Pokedex", style = MaterialTheme.typography.headlineLarge, fontWeight = FontWeight.Bold)
                Spacer(Modifier.padding(4.dp))
                Text("Busca tu pókemon por nombre o filtra arriba por tu tipo favorito", style = MaterialTheme.typography.bodyMedium)
                Spacer(Modifier.padding(4.dp))
                OutlinedTextField("", {}, modifier = Modifier.fillMaxWidth())
                Spacer(Modifier.padding(8.dp))
                PokemonsList(pokemons)
            }
            /*LottieAnimation(
                composition = composition2,
                progress = { progress2 },
            )
            Column(Modifier.padding(paddingValues)) {
                Text("¡Bienvenido a PokeApp!")
                LottieAnimation(
                    composition = composition,
                    progress = { progress },
                    modifier = Modifier.weight(0.4f)
                )
                Text("Esta App solo es una demo tecnica sencilla de arquitectura limpia y buenas practicas")
                Text("Puedes ver mas informacion de la arquitectura y las tecnologias en la pagina de GitHub")
                Text("© Hugo Polo 2025")
                PokemonsList(pokemons, modifier = Modifier.weight(0.6f))
            }*/
        }
    }


}

@Composable
fun PokemonsList(pokemons: LazyPagingItems<PokemonModel>, modifier: Modifier = Modifier) {
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(
            count = pokemons.itemCount,
            key = { index -> pokemons[index]?.id ?: index } // clave única
        ) { index ->
            pokemons[index]?.let { pokemon ->
                PokemonItem(pokemon = pokemon)
            }
        }
    }
}

@Composable
fun PokemonItem(pokemon: PokemonModel) {
    Log.d("PokemonItem", "Pokemon: $pokemon")
    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .fillMaxWidth()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        pokemon.type.first().getColor().copy(alpha = 0.7f), // más transparente arriba
                        pokemon.type.first().getColor()  // más intenso abajo
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
                contentDescription = "Pokeball",
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

        /*  PokemonSpriteWithPokeballOutline(
              spriteUrl = pokemon.sprite,
          )*/

    }
}

@Composable
fun PokemonSpriteWithPokeballOutline(
    spriteUrl: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .aspectRatio(1f)
            .padding(10.dp), // tamaño del círculo exterior
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.matchParentSize()) {
            val strokeWidth = 6.dp.toPx()
            val radius = size.minDimension / 2

            // Fondo blanco interno
            drawCircle(
                color = Color(0xFFF8F8F8),
                radius = radius
            )

            // Arco rojo arriba (fino)
            drawArc(
                color = Color.Red,
                startAngle = 180f,
                sweepAngle = 180f,
                useCenter = false,
                style = Stroke(width = strokeWidth)
            )

            // Arco negro abajo (fino)
            drawArc(
                color = Color.Black,
                startAngle = 0f,
                sweepAngle = 180f,
                useCenter = false,
                style = Stroke(width = strokeWidth)
            )

            // Línea negra central
            /* drawLine(
                 color = Color.Black,
                 start = Offset(0f, radius),
                 end = Offset(size.width, radius),
                 strokeWidth = strokeWidth / 2
             )*/
        }

        // Sprite en el centro
        AsyncImage(
            model = spriteUrl,
            contentDescription = null,
            modifier = Modifier
                .padding(8.dp)
                .size(80.dp)
        )
    }
}

