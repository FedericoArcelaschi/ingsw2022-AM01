package it.polimi.ingsw.android.network

import android.util.Log
import it.polimi.ingsw.client.communication.ClientMain
import it.polimi.ingsw.client.userInterface.UserInterface
import it.polimi.ingsw.communication.message.subclasses.EndGame
import it.polimi.ingsw.communication.message.subclasses.LobbyInfo
import it.polimi.ingsw.communication.modelData.BoardData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.net.InetSocketAddress

private const val TAG = "ClientNetworkController"

/**
 * Process-scoped owner of the single [ClientMain] connection. Implements [UserInterface] so
 * network-thread callbacks (fired from ClientReceiver's executor) can be forwarded to Compose.
 *
 * All state is exposed as [kotlinx.coroutines.flow.StateFlow] rather than a replay=0 SharedFlow
 * of one-shot events: a screen's ViewModel is created (and starts collecting) slightly *after*
 * navigation is triggered by a state change on the *previous* screen, so a plain event stream
 * can drop the very update that caused the navigation. StateFlow always replays its latest value
 * to a new collector, which avoids that race by construction.
 */
class ClientNetworkController {

    private val _connected = MutableStateFlow(false)
    val connected = _connected.asStateFlow()

    private val _lobbyInfo = MutableStateFlow<LobbyInfo?>(null)
    val lobbyInfo = _lobbyInfo.asStateFlow()

    private val _boardData = MutableStateFlow<BoardData?>(null)
    val boardData = _boardData.asStateFlow()

    private val _lastError = MutableStateFlow<String?>(null)
    val lastError = _lastError.asStateFlow()

    private val _endGame = MutableStateFlow<EndGame?>(null)
    val endGame = _endGame.asStateFlow()

    val clientMain: ClientMain = ClientMain(NetworkUserInterface())

    fun connect(host: String, port: Int) {
        clientMain.connect(InetSocketAddress(host, port))
        _connected.value = clientMain.isConnected
    }

    fun clearError() {
        _lastError.value = null
    }

    private inner class NetworkUserInterface : UserInterface {
        override fun draw(boardData: BoardData) {
            Log.d(TAG, "draw: received BoardData for ${boardData.username()}")
            clientMain.setBoardData(boardData)
            _boardData.value = boardData
        }

        override fun printLobby(lobbyInfo: LobbyInfo) {
            Log.d(TAG, "printLobby: $lobbyInfo")
            _lobbyInfo.value = lobbyInfo
        }

        override fun printError(error: String) {
            Log.w(TAG, "printError: $error")
            _lastError.value = error
        }

        override fun endCurrentGame(endGameMessage: EndGame) {
            Log.d(TAG, "endCurrentGame: ${endGameMessage.cause}")
            _endGame.value = endGameMessage
        }

        override fun disconnected() {
            Log.w(TAG, "disconnected")
            _connected.value = false
        }
    }
}
