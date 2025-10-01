package com.hugopolog.demoappopen.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.hugopolog.demoappopen.R
import com.hugopolog.demoappopen.util.getColor
import com.hugopolog.demoappopen.util.getImage
import com.hugopolog.domain.entities.pokemon.PokemonType


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TypeSelectorDialog(
    selectedTypes : List<PokemonType>,
    onDismiss: () -> Unit,
    onConfirm: (List<PokemonType>) -> Unit
) {

    var selectedTypes by remember { mutableStateOf(selectedTypes) }

    ModalBottomSheet(onDismissRequest = onDismiss) {
        Text(
            stringResource(R.string.filter_by_type),
            fontWeight = FontWeight.SemiBold,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(16.dp).align(Alignment.CenterHorizontally)
        )

        FlowRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.Center,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            PokemonType.entries.forEach { type ->
                val isSelected = selectedTypes.contains(type)

                Box(
                    modifier = Modifier
                        .padding(4.dp)
                        .size(52.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(
                            color = if (isSelected) type.getColor()
                            else Color.LightGray.copy(alpha = 0.3f)
                        )
                        .clickable {
                            selectedTypes = if (isSelected) {
                                selectedTypes - type
                            } else {
                                selectedTypes + type
                            }
                        }
                        .padding(8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = type.getImage()),
                        contentDescription = type.name,
                        modifier = Modifier.size(40.dp)
                    )
                }
            }
        }

        Row(
            Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.End
        ) {
            TextButton(onClick = onDismiss) { Text(stringResource(R.string.cancel)) }
            TextButton(onClick = { onConfirm(selectedTypes.toList()) }) { Text(stringResource(R.string.filter)) }
        }
    }
}

