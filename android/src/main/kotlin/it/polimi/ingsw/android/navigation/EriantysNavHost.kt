package it.polimi.ingsw.android.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import it.polimi.ingsw.android.ui.connect.ConnectScreen
import it.polimi.ingsw.android.ui.connect.ConnectViewModel
import it.polimi.ingsw.android.ui.game.GameScreen
import it.polimi.ingsw.android.ui.lobby.LobbyScreen
import it.polimi.ingsw.android.ui.lobby.LobbyViewModel
import it.polimi.ingsw.android.ui.preferences.PreferencesScreen
import it.polimi.ingsw.android.ui.preferences.PreferencesViewModel

private object Routes {
    const val CONNECT = "connect"
    const val PREFERENCES = "preferences"
    const val LOBBY = "lobby"
    const val GAME = "game"
}

@Composable
fun EriantysNavHost(navController: NavHostController = rememberNavController()) {
    NavHost(navController = navController, startDestination = Routes.CONNECT) {
        composable(Routes.CONNECT) {
            val viewModel: ConnectViewModel = viewModel()
            val uiState by viewModel.uiState.collectAsState()

            LaunchedEffect(uiState.isConnected) {
                if (uiState.isConnected) {
                    navController.navigate(Routes.PREFERENCES)
                }
            }

            ConnectScreen(viewModel)
        }

        composable(Routes.PREFERENCES) {
            val viewModel: PreferencesViewModel = viewModel()
            val uiState by viewModel.uiState.collectAsState()

            LaunchedEffect(uiState.submitted) {
                if (uiState.submitted) {
                    navController.navigate(Routes.LOBBY)
                }
            }

            PreferencesScreen(viewModel)
        }

        composable(Routes.LOBBY) {
            val viewModel: LobbyViewModel = viewModel()
            val uiState by viewModel.uiState.collectAsState()

            LaunchedEffect(uiState.gameStarted) {
                if (uiState.gameStarted) {
                    navController.navigate(Routes.GAME)
                }
            }

            LobbyScreen(viewModel)
        }

        composable(Routes.GAME) {
            GameScreen()
        }
    }
}
