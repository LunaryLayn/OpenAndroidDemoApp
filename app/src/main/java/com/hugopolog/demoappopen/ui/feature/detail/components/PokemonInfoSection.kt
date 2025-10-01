/*
 * © 2025 Hugo Polo - Github: https://github.com/LunaryLayn
 */

package com.hugopolog.demoappopen.ui.feature.detail.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.hugopolog.demoappopen.R
import com.hugopolog.domain.entities.pokemon.GenderDistribution
import com.hugopolog.domain.entities.pokemon.PokemonModel

@Composable
fun PokemonInfoContent(pokemon: PokemonModel) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            InfoSection(title = stringResource(R.string.about)) {
                InfoRow(label = stringResource(R.string.weight), value = "${pokemon.weight} kg")
                InfoRow(label = stringResource(R.string.height), value = "${pokemon.height} m")
                InfoRow(label = stringResource(R.string.base_experience), value = "${pokemon.baseExperience}")
                InfoRow(
                    label = stringResource(R.string.generation),
                    value = pokemon.generation.replaceFirstChar { it.uppercase() }
                )
            }
        }

        item {
            InfoSection(title = stringResource(R.string.gender)) {
                when (val gender = pokemon.gender) {
                    is GenderDistribution.WithGender -> {
                        InfoRow(label = stringResource(R.string.male), value = "${gender.malePercent}%")
                        InfoRow(label = stringResource(R.string.female), value = "${gender.femalePercent}%")
                    }

                    GenderDistribution.Genderless -> {
                        InfoRow(label = stringResource(R.string.gender), value = stringResource(R.string.genderless))
                    }
                }
            }
        }

        item {
            InfoSection(title = stringResource(R.string.abilities)) {
                Text(
                    text = pokemon.abilities.joinToString(", "),
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}

@Composable
private fun InfoSection(
    title: String,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )
        Column(content = content)
    }
}

@Composable
private fun InfoRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Medium
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}