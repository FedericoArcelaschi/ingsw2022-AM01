package it.polimi.ingsw.android.ui.preferences

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

private val PLAYER_COUNTS = listOf(2, 3, 4)

@Composable
fun PreferencesScreen(
    viewModel: PreferencesViewModel = viewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text("Join a game", style = MaterialTheme.typography.headlineMedium)

        OutlinedTextField(
            value = uiState.username,
            onValueChange = viewModel::onUsernameChanged,
            label = { Text("Username") },
            singleLine = true,
            modifier = Modifier.padding(top = 32.dp),
        )

        Text("Players", modifier = Modifier.padding(top = 24.dp))
        SingleChoiceSegmentedButtonRow(modifier = Modifier.padding(top = 8.dp)) {
            PLAYER_COUNTS.forEachIndexed { index, count ->
                SegmentedButton(
                    selected = uiState.playerCount == count,
                    onClick = { viewModel.onPlayerCountChanged(count) },
                    shape = SegmentedButtonDefaults.itemShape(index = index, count = PLAYER_COUNTS.size),
                ) {
                    Text("$count")
                }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth(0.7f)
                .padding(top = 24.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text("Expert mode")
            Switch(checked = uiState.expertMode, onCheckedChange = viewModel::onExpertModeChanged)
        }

        Button(
            onClick = viewModel::submit,
            enabled = !uiState.submitted,
            modifier = Modifier.padding(top = 24.dp),
        ) {
            Text(if (uiState.submitted) "Waiting…" else "Join lobby")
        }

        uiState.errorMessage?.let { error ->
            Text(
                error,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(top = 16.dp),
            )
        }
    }
}
