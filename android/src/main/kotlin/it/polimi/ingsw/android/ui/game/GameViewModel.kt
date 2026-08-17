package it.polimi.ingsw.android.ui.game

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import it.polimi.ingsw.android.EriantysApplication
import it.polimi.ingsw.communication.command.Command
import it.polimi.ingsw.communication.command.CommandType
import it.polimi.ingsw.communication.modelData.BoardData
import it.polimi.ingsw.communication.modelData.expertMode.CharacterData
import it.polimi.ingsw.server.model.baseLogic.StudentColor
import it.polimi.ingsw.server.model.baseLogic.TurnPhase
import it.polimi.ingsw.server.model.expertLogic.character.costants.CharacterInputs
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.text.ParseException

/**
 * Extra input a character card's effect needs before PAY_CHARACTER can be sent, derived from
 * [CharacterInputs]. Card parameters are plain color-name/island-number tokens with no notion of
 * *which* physical student instance they came from (see Command's PAY_CHARACTER parsing), so
 * colors are picked from a generic swatch rather than from a specific waiting-room/card slot.
 */
sealed interface PendingCharacterInput {
    data class AwaitingColors(
        val charName: String,
        val validCounts: Set<Int>,
        val selected: List<StudentColor> = emptyList(),
        val thenAwaitsIsland: Boolean = false,
    ) : PendingCharacterInput

    data class AwaitingIsland(
        val charName: String,
        val extraColors: List<StudentColor> = emptyList(),
    ) : PendingCharacterInput
}

data class GameUiState(
    val boardData: BoardData? = null,
    // Indices into myCastle().waitingRoom(), not colors: two students of the same color are
    // distinct selectable tokens (mirrors the JavaFX reference's per-instance ToggleButtons), so
    // keying selection by color alone would make picking "2 greens" collapse to a single toggle.
    val selectedWaitingRoomIndices: Set<Int> = emptySet(),
    val pendingCharacterInput: PendingCharacterInput? = null,
    val lastError: String? = null,
    val disconnected: Boolean = false,
)

class GameViewModel(application: Application) : AndroidViewModel(application) {

    private val networkController = (application as EriantysApplication).networkController

    private val _uiState = MutableStateFlow(GameUiState())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            networkController.boardData.collect { boardData ->
                if (boardData != null) {
                    // Selections reference waiting-room contents which change after every move;
                    // stale selections from the previous board snapshot would silently point at
                    // the wrong students, so clear them on every update rather than try to migrate.
                    _uiState.value = _uiState.value.copy(
                        boardData = boardData,
                        selectedWaitingRoomIndices = emptySet(),
                        pendingCharacterInput = null,
                    )
                }
            }
        }
        viewModelScope.launch {
            networkController.lastError.collect { error ->
                if (error != null) _uiState.value = _uiState.value.copy(lastError = error)
            }
        }
        viewModelScope.launch {
            networkController.connected.collect { connected ->
                if (!connected) _uiState.value = _uiState.value.copy(disconnected = true)
            }
        }
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(lastError = null)
    }

    private val maxWaitingRoomSelection: Int
        get() = if (_uiState.value.boardData?.nPlayer() == 3) 4 else 3

    fun onWaitingRoomStudentClick(index: Int) {
        val state = _uiState.value
        val selected = state.selectedWaitingRoomIndices
        val newSelection = if (index in selected) {
            selected - index
        } else if (selected.size < maxWaitingRoomSelection) {
            selected + index
        } else {
            return
        }
        _uiState.value = state.copy(selectedWaitingRoomIndices = newSelection)
    }

    private fun selectedStudentNames(): List<String>? {
        val waitingRoom = _uiState.value.boardData?.myCastle()?.waitingRoom() ?: return null
        val selected = _uiState.value.selectedWaitingRoomIndices
        if (selected.isEmpty()) return null
        return selected.sorted().map { index -> waitingRoom[index].name }
    }

    fun onConfirmMoveToDiningRoom() {
        val studentNames = selectedStudentNames() ?: return
        sendCommand(CommandType.MOVE_STUDENT_TO_CASTLE, studentNames)
        _uiState.value = _uiState.value.copy(selectedWaitingRoomIndices = emptySet())
    }

    fun onIslandClick(islandIndex: Int) {
        val boardData = _uiState.value.boardData ?: return
        val islandId = (islandIndex + 1).toString()

        val pending = _uiState.value.pendingCharacterInput
        if (pending is PendingCharacterInput.AwaitingIsland) {
            val parameters = listOf(pending.charName, islandId) + pending.extraColors.map { it.name }
            sendCommand(CommandType.PAY_CHARACTER, parameters)
            _uiState.value = _uiState.value.copy(pendingCharacterInput = null)
            return
        }

        when (boardData.turn().currentPhase()) {
            TurnPhase.STUDENTS -> {
                val studentNames = selectedStudentNames() ?: return
                sendCommand(CommandType.MOVE_STUDENT_TO_ISLAND, listOf(islandId) + studentNames)
                _uiState.value = _uiState.value.copy(selectedWaitingRoomIndices = emptySet())
            }
            TurnPhase.MOTHERNATURE -> {
                val currentPosition = boardData.motherNaturePosition() + 1
                var shift = (islandIndex + 1) - currentPosition
                if (shift <= 0) shift += boardData.islandList().size
                sendCommand(CommandType.MOVE_MOTHER_NATURE, listOf(shift.toString()))
            }
            else -> Unit
        }
    }

    fun onCloudClick(cloudIndex: Int) {
        sendCommand(CommandType.CHOOSE_CLOUD, listOf((cloudIndex + 1).toString()))
    }

    fun onCardClick(cardId: Int) {
        sendCommand(CommandType.PLAY_CARD, listOf(cardId.toString()))
    }

    fun onCharacterClick(character: CharacterData) {
        val inputs = try {
            CharacterInputs.getChar(character.name)
        } catch (e: ParseException) {
            return
        }
        val needsIsland = Integer::class.java in inputs.types
        val needsColors = StudentColor::class.java in inputs.types

        _uiState.value = _uiState.value.copy(
            pendingCharacterInput = when {
                needsColors -> PendingCharacterInput.AwaitingColors(
                    charName = character.name,
                    validCounts = inputs.numberOfStudent ?: setOf(0),
                    thenAwaitsIsland = needsIsland,
                )
                needsIsland -> PendingCharacterInput.AwaitingIsland(character.name)
                else -> {
                    sendCommand(CommandType.PAY_CHARACTER, listOf(character.name))
                    null
                }
            },
        )
    }

    fun onCharacterColorPick(color: StudentColor) {
        val pending = _uiState.value.pendingCharacterInput
        if (pending !is PendingCharacterInput.AwaitingColors) return

        val maxCount = pending.validCounts.max()
        val newSelected = if (color in pending.selected) {
            pending.selected - color
        } else if (pending.selected.size < maxCount) {
            pending.selected + color
        } else {
            return
        }

        if (newSelected.size in pending.validCounts) {
            if (pending.thenAwaitsIsland) {
                _uiState.value = _uiState.value.copy(
                    pendingCharacterInput = PendingCharacterInput.AwaitingIsland(pending.charName, newSelected),
                )
            } else {
                sendCommand(CommandType.PAY_CHARACTER, listOf(pending.charName) + newSelected.map { it.name })
                _uiState.value = _uiState.value.copy(pendingCharacterInput = null)
            }
        } else {
            _uiState.value = _uiState.value.copy(pendingCharacterInput = pending.copy(selected = newSelected))
        }
    }

    fun onCancelCharacterInput() {
        _uiState.value = _uiState.value.copy(pendingCharacterInput = null)
    }

    private fun sendCommand(type: CommandType, parameters: List<String>) {
        viewModelScope.launch(Dispatchers.IO) {
            val command = Command(type, parameters)
            networkController.clientMain.runCommand(command)
        }
    }
}
