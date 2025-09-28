package com.hugopolog.demoappopen.ui.feature.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.hugopolog.demoappopen.R

@Composable
fun HomeScreen() {

    HomeContent()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeContent() {
    Scaffold(
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
        }
    ) { paddingValues ->

        val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.bulbasaur))
        val progress by animateLottieCompositionAsState(
            composition,
            iterations = LottieConstants.IterateForever,
            isPlaying = true,
            speed = 0.8f
        )

        Box(modifier = Modifier
            .padding(paddingValues)
            .fillMaxSize()) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text("Bienvenido a PokeApp!", style = MaterialTheme.typography.headlineMedium)
                LottieAnimation(
                    composition = composition,
                    progress = { progress },
                )
            }
        }
    }
}