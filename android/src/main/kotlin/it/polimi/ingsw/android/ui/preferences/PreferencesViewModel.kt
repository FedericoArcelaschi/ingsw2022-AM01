package it.polimi.ingsw.android.ui.preferences

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import it.polimi.ingsw.android.EriantysApplication
import it.polimi.ingsw.communication.message.subclasses.Preferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class PreferencesUiState(
    val username: String = "",
    val playerCount: Int = 2,
    val expertMode: Boolean = false,
    val submitted: Boolean = false,
    val errorMessage: String? = null,
)

class PreferencesViewModel(application: Application) : AndroidViewModel(application) {

    private val networkController = (application as EriantysApplication).networkController

    private val _uiState = MutableStateFlow(PreferencesUiState())
    val uiState = _uiState.asStateFlow()

    fun onUsernameChanged(username: String) {
        _uiState.value = _uiState.value.copy(username = username)
    }

    fun onPlayerCountChanged(playerCount: Int) {
        _uiState.value = _uiState.value.copy(playerCount = playerCount)
    }

    fun onExpertModeChanged(expertMode: Boolean) {
        _uiState.value = _uiState.value.copy(expertMode = expertMode)
    }

    fun submit() {
        val state = _uiState.value
        if (state.username.isBlank()) {
            _uiState.value = state.copy(errorMessage = "Username can't be empty")
            return
        }
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val preferences = Preferences(state.username, state.playerCount, state.expertMode)
                networkController.clientMain.sendPreferences(preferences)
                _uiState.value = _uiState.value.copy(submitted = true, errorMessage = null)
            } catch (e: Exception) {
                // GameType.getGameType is declared to throw IllegalAccessException but actually
                // throws IllegalArgumentException for an unsupported player count/mode combo.
                _uiState.value = _uiState.value.copy(errorMessage = e.message)
            }
        }
    }
}
