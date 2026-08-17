package it.polimi.ingsw.android.ui.connect

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun ConnectScreen(
    viewModel: ConnectViewModel = viewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text("Eriantys", style = MaterialTheme.typography.headlineMedium)

        Column(modifier = Modifier.padding(top = 32.dp)) {
            OutlinedTextField(
                value = uiState.host,
                onValueChange = viewModel::onHostChanged,
                label = { Text("Server address") },
                singleLine = true,
                modifier = Modifier.padding(bottom = 8.dp),
            )
            OutlinedTextField(
                value = uiState.port,
                onValueChange = viewModel::onPortChanged,
                label = { Text("Port") },
                singleLine = true,
            )
        }

        Button(
            onClick = viewModel::connect,
            enabled = !uiState.isConnecting,
            modifier = Modifier.padding(top = 24.dp),
        ) {
            Text(if (uiState.isConnecting) "Connecting…" else "Connect")
        }

        if (uiState.isConnecting) {
            CircularProgressIndicator(modifier = Modifier.padding(top = 16.dp))
        }

        if (uiState.isConnected) {
            Text(
                "Connected!",
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(top = 16.dp),
            )
        }

        uiState.lastError?.let { error ->
            Text(
                error,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(top = 16.dp),
            )
        }
    }
}
