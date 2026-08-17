package it.polimi.ingsw.android.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val ErinantysBlue = Color(0xFF1B2A4A)
private val ErinantysGold = Color(0xFFE8B84B)

private val LightColors = lightColorScheme(
    primary = ErinantysBlue,
    secondary = ErinantysGold,
)

private val DarkColors = darkColorScheme(
    primary = ErinantysGold,
    secondary = ErinantysBlue,
)

@Composable
fun EriantysTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val colorScheme = if (darkTheme) DarkColors else LightColors
    MaterialTheme(colorScheme = colorScheme, content = content)
}
