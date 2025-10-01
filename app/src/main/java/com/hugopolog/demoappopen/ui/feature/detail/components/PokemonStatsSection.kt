package com.hugopolog.demoappopen.ui.feature.detail.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.hugopolog.demoappopen.R
import com.hugopolog.demoappopen.util.getColor
import com.hugopolog.domain.entities.pokemon.PokemonModel


@Composable
fun StatsTabContent(pokemon: PokemonModel) {
    val maxStatValue = 255
    val maxPokemonTotalValue = 720

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(32.dp)
    ) {
        Text(
            text = stringResource(R.string.base_stats),
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        pokemon.stats.forEach { (statName, statValue) ->
            val progress = statValue / maxStatValue.toFloat()
            val barColor = if (progress >= 0.7f) {
                Color.Green
            } else {
                lerp(Color.Red, Color.Green, progress / 0.7f)
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
            ) {
                Text(
                    text = statName,
                    modifier = Modifier.weight(0.4f),
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = statValue.toString(),
                    modifier = Modifier.weight(0.2f),
                    style = MaterialTheme.typography.bodyMedium
                )
                LinearProgressIndicator(
                    progress = { progress },
                    modifier = Modifier
                        .weight(0.5f)
                        .height(8.dp)
                        .clip(RoundedCornerShape(4.dp)),
                    gapSize = (-15).dp,
                    drawStopIndicator = {},
                    color = barColor
                )
            }
        }

        HorizontalDivider(
            color = pokemon.types.first().getColor(),
            modifier = Modifier.padding(vertical = 8.dp)
        )

        val total = pokemon.stats.values.sum()
        val totalProgress = total / maxPokemonTotalValue.toFloat()
        val totalColor = if (totalProgress >= 0.7f) {
            Color.Green
        } else {
            lerp(Color.Red, Color.Green, totalProgress / 0.7f)
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = stringResource(R.string.total),
                modifier = Modifier.weight(0.4f),
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = total.toString(),
                modifier = Modifier.weight(0.2f),
                style = MaterialTheme.typography.bodyMedium
            )
            LinearProgressIndicator(
                progress = { totalProgress },
                modifier = Modifier
                    .weight(0.5f)
                    .height(8.dp)
                    .clip(RoundedCornerShape(4.dp)),
                gapSize = (-15).dp,
                drawStopIndicator = {},
                color = totalColor
            )
        }
    }
}