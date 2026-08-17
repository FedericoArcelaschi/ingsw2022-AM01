package it.polimi.ingsw.android.ui.game

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun GameScreen(
    viewModel: GameViewModel = viewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()
    val boardData = uiState.boardData

    Box(modifier = Modifier.fillMaxSize()) {
        if (boardData == null) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        } else {
            Column(modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())) {
                TurnBanner(boardData.turn())
                AssistantCardHand(boardData.myCastle().deck(), onCardClick = viewModel::onCardClick)
                CloudsRow(boardData.cloudList(), onCloudClick = viewModel::onCloudClick)
                IslandsRow(boardData.islandList(), boardData.motherNaturePosition(), onIslandClick = viewModel::onIslandClick)
                MyCastleView(
                    castle = boardData.myCastle(),
                    nPlayer = boardData.nPlayer(),
                    selectedIndices = uiState.selectedWaitingRoomIndices,
                    onStudentClick = viewModel::onWaitingRoomStudentClick,
                    onConfirmMoveToDiningRoom = viewModel::onConfirmMoveToDiningRoom,
                )
                OtherCastlesRow(boardData.otherCastles(), boardData.nPlayer())
            }
        }

        uiState.lastError?.let { error ->
            Snackbar(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(16.dp),
                action = { TextButton(onClick = viewModel::clearError) { Text("Dismiss") } },
            ) {
                Text(error)
            }
        }
    }
}
