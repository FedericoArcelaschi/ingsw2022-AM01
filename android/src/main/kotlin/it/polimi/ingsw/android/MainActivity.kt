package it.polimi.ingsw.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import it.polimi.ingsw.android.navigation.EriantysNavHost
import it.polimi.ingsw.android.ui.theme.EriantysTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            EriantysTheme {
                Surface(modifier = Modifier
                    .fillMaxSize()
                    .systemBarsPadding()) {
                    EriantysNavHost()
                }
            }
        }
    }
}
