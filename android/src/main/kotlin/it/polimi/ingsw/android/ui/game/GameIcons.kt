package it.polimi.ingsw.android.ui.game

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import it.polimi.ingsw.android.R
import it.polimi.ingsw.server.model.baseLogic.StudentColor
import it.polimi.ingsw.server.model.baseLogic.Team

@Composable
fun StudentIcon(
    color: StudentColor,
    modifier: Modifier = Modifier,
    size: androidx.compose.ui.unit.Dp = 22.dp,
    selected: Boolean = false,
    onClick: (() -> Unit)? = null,
) {
    Image(
        painter = painterResource(color.studentIconRes()),
        contentDescription = color.name,
        modifier = modifier
            .size(size)
            .clip(CircleShape)
            .then(if (selected) Modifier.border(2.dp, Color.Black, CircleShape) else Modifier)
            .then(if (onClick != null) Modifier.clickable { onClick() } else Modifier),
    )
}

@Composable
fun TeacherIcon(color: StudentColor, modifier: Modifier = Modifier, size: androidx.compose.ui.unit.Dp = 26.dp) {
    Image(
        painter = painterResource(color.teacherIconRes()),
        contentDescription = "${color.name} teacher",
        modifier = modifier.size(size),
    )
}

@Composable
fun TeacherSlot(color: StudentColor, owned: Boolean, modifier: Modifier = Modifier, size: androidx.compose.ui.unit.Dp = 26.dp) {
    if (owned) {
        TeacherIcon(color, modifier, size)
    } else {
        androidx.compose.foundation.layout.Box(
            modifier
                .size(size)
                .clip(CircleShape)
                .background(Color.LightGray.copy(alpha = 0.3f)),
        )
    }
}

@Composable
fun TowerIcon(team: Team, modifier: Modifier = Modifier, size: androidx.compose.ui.unit.Dp = 24.dp) {
    Image(
        painter = painterResource(team.towerIconRes()),
        contentDescription = "${team.name} tower",
        modifier = modifier.size(size),
    )
}

@Composable
fun MotherNatureIcon(modifier: Modifier = Modifier, size: androidx.compose.ui.unit.Dp = 26.dp) {
    Image(
        painter = painterResource(R.drawable.mother_nature),
        contentDescription = "Mother Nature",
        modifier = modifier.size(size),
    )
}

@Composable
fun CoinIcon(modifier: Modifier = Modifier, size: androidx.compose.ui.unit.Dp = 22.dp) {
    Image(
        painter = painterResource(R.drawable.coin),
        contentDescription = "Coin",
        modifier = modifier.size(size),
    )
}
