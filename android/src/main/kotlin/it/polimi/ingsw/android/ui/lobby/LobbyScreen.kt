package it.polimi.ingsw.android.ui.lobby

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import it.polimi.ingsw.communication.message.subclasses.LobbyInfo

@Composable
fun LobbyScreen(
    viewModel: LobbyViewModel = viewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text("Waiting for players…", style = MaterialTheme.typography.headlineSmall)

        if (uiState.gameStarted) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text("Game started!", style = MaterialTheme.typography.headlineMedium)
            }
            return@Column
        }

        if (uiState.disconnected) {
            Text(
                "Disconnected from server",
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(top = 16.dp),
            )
        }

        CircularProgressIndicator(modifier = Modifier.padding(vertical = 16.dp))

        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            items(uiState.lobbies) { lobby ->
                LobbyRow(lobby)
            }
        }
    }
}

@Composable
private fun LobbyRow(lobby: LobbyInfo.Lobby) {
    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 4.dp)) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(lobby.gameType.name, style = MaterialTheme.typography.titleMedium)
            Text(
                if (lobby.connectedPlayers.isEmpty()) "No players yet" else lobby.formattedPlayers,
                style = MaterialTheme.typography.bodyMedium,
            )
        }
    }
}
