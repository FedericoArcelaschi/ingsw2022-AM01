package it.polimi.ingsw.android.ui.game

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import it.polimi.ingsw.communication.modelData.expertMode.CharacterData
import it.polimi.ingsw.server.model.baseLogic.StudentColor

@Composable
fun CharacterCardsRow(
    characters: List<CharacterData>,
    activeCharacterName: String?,
    onCharacterClick: (CharacterData) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyRow(modifier = modifier, horizontalArrangement = Arrangement.spacedBy(8.dp), contentPadding = PaddingValues(12.dp)) {
        items(characters) { character ->
            CharacterCard(character, active = character.name.equals(activeCharacterName, ignoreCase = true), onClick = { onCharacterClick(character) })
        }
    }
}

@Composable
private fun CharacterCard(character: CharacterData, active: Boolean, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .width(120.dp)
            .clickable { onClick() },
        border = if (active) BorderStroke(2.dp, MaterialTheme.colorScheme.primary) else null,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.tertiaryContainer),
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            Text(character.name.lowercase().replaceFirstChar { it.uppercase() }, style = MaterialTheme.typography.labelLarge)
            if (character.students.isNotEmpty()) {
                Row(horizontalArrangement = Arrangement.spacedBy(2.dp)) {
                    character.students.forEach { color -> StudentIcon(color, size = 14.dp) }
                }
            }
        }
    }
}

/** Inline swatch shown while a [PendingCharacterInput.AwaitingColors] is in progress. */
@Composable
fun CharacterColorPicker(
    pending: PendingCharacterInput.AwaitingColors,
    onColorPick: (StudentColor) -> Unit,
    onCancel: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier
        .fillMaxWidth()
        .padding(12.dp)) {
        Text(
            "${pending.charName}: pick ${pending.validCounts.sorted().joinToString(" or ")} student color(s)",
            style = MaterialTheme.typography.labelMedium,
        )
        Row(modifier = Modifier.padding(top = 8.dp), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            StudentColor.values().forEach { color ->
                val count = pending.selected.count { it == color }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    StudentIcon(color, size = 32.dp, onClick = { onColorPick(color) })
                    if (count > 0) Text("$count", style = MaterialTheme.typography.labelSmall)
                }
            }
            TextButton(onClick = onCancel) { Text("Cancel") }
        }
    }
}
