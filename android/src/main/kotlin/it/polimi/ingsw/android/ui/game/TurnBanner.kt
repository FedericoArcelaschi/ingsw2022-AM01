package it.polimi.ingsw.android.ui.game

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import it.polimi.ingsw.communication.modelData.TurnData

@Composable
fun TurnBanner(turn: TurnData, modifier: Modifier = Modifier) {
    Row(modifier = modifier
        .fillMaxWidth()
        .padding(12.dp)) {
        Text("Phase: ${turn.currentPhase()}", style = MaterialTheme.typography.titleMedium)
    }
    Row(modifier = modifier
        .fillMaxWidth()
        .padding(horizontal = 12.dp)) {
        turn.actionOrder().forEachIndexed { index, player ->
            if (index != 0) Text(", ")
            Text(
                player,
                fontWeight = if (player == turn.currentPlayer()) FontWeight.Bold else FontWeight.Normal,
                color = if (player == turn.currentPlayer()) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface,
            )
        }
    }
}
