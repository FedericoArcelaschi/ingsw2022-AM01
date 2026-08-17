package it.polimi.ingsw.android.ui.connect

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import it.polimi.ingsw.android.EriantysApplication
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class ConnectUiState(
    val host: String = "",
    val port: String = "12345",
    val isConnecting: Boolean = false,
    val isConnected: Boolean = false,
    val lastError: String? = null,
)

class ConnectViewModel(application: Application) : AndroidViewModel(application) {

    private val networkController = (application as EriantysApplication).networkController

    private val _uiState = MutableStateFlow(ConnectUiState())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            networkController.lastError.collect { error ->
                if (error != null) _uiState.value = _uiState.value.copy(lastError = error)
            }
        }
        viewModelScope.launch {
            networkController.connected.collect { connected ->
                _uiState.value = _uiState.value.copy(isConnected = connected, isConnecting = false)
            }
        }
    }

    fun onHostChanged(host: String) {
        _uiState.value = _uiState.value.copy(host = host)
    }

    fun onPortChanged(port: String) {
        _uiState.value = _uiState.value.copy(port = port)
    }

    fun connect() {
        val port = _uiState.value.port.toIntOrNull() ?: return
        val host = _uiState.value.host.trim()
        if (host.isEmpty()) return

        _uiState.value = _uiState.value.copy(isConnecting = true, lastError = null)
        viewModelScope.launch(Dispatchers.IO) {
            networkController.connect(host, port)
        }
    }
}
