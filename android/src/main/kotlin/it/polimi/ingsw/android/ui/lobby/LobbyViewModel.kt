package it.polimi.ingsw.android.ui.lobby

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import it.polimi.ingsw.android.EriantysApplication
import it.polimi.ingsw.communication.message.subclasses.LobbyInfo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class LobbyUiState(
    val lobbies: List<LobbyInfo.Lobby> = emptyList(),
    val gameStarted: Boolean = false,
    val disconnected: Boolean = false,
)

class LobbyViewModel(application: Application) : AndroidViewModel(application) {

    private val networkController = (application as EriantysApplication).networkController

    private val _uiState = MutableStateFlow(LobbyUiState())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            networkController.lobbyInfo.collect { lobbyInfo ->
                if (lobbyInfo != null) {
                    _uiState.value = _uiState.value.copy(lobbies = lobbyInfo.lobbies)
                }
            }
        }
        viewModelScope.launch {
            networkController.boardData.collect { boardData ->
                if (boardData != null) {
                    _uiState.value = _uiState.value.copy(gameStarted = true)
                }
            }
        }
        viewModelScope.launch {
            networkController.connected.collect { connected ->
                if (!connected) {
                    _uiState.value = _uiState.value.copy(disconnected = true)
                }
            }
        }
    }
}
