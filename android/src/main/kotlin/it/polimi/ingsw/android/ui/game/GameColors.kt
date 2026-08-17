package it.polimi.ingsw.android.ui.game

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.Color
import it.polimi.ingsw.android.R
import it.polimi.ingsw.server.model.baseLogic.StudentColor
import it.polimi.ingsw.server.model.baseLogic.Team

/** Flat-color fallback, used only where a tint (not an image) is needed, e.g. container accents. */
fun StudentColor.toComposeColor(): Color = when (this) {
    StudentColor.GREEN -> Color(0xFF4CAF50)
    StudentColor.RED -> Color(0xFFE53935)
    StudentColor.YELLOW -> Color(0xFFFDD835)
    StudentColor.PINK -> Color(0xFFEC407A)
    StudentColor.BLUE -> Color(0xFF42A5F5)
}

fun Team.toComposeColor(): Color = when (this) {
    Team.BLACK -> Color(0xFF212121)
    Team.WHITE -> Color(0xFFEEEEEE)
    Team.GREY -> Color(0xFF9E9E9E)
}

/** Same student/teacher/tower art bundled with the desktop client (see fxml/css/style.css). */
@DrawableRes
fun StudentColor.studentIconRes(): Int = when (this) {
    StudentColor.GREEN -> R.drawable.student_green
    StudentColor.RED -> R.drawable.student_red
    StudentColor.YELLOW -> R.drawable.student_yellow
    StudentColor.PINK -> R.drawable.student_pink
    StudentColor.BLUE -> R.drawable.student_blue
}

@DrawableRes
fun StudentColor.teacherIconRes(): Int = when (this) {
    StudentColor.GREEN -> R.drawable.teacher_green
    StudentColor.RED -> R.drawable.teacher_red
    StudentColor.YELLOW -> R.drawable.teacher_yellow
    StudentColor.PINK -> R.drawable.teacher_pink
    StudentColor.BLUE -> R.drawable.teacher_blue
}

@DrawableRes
fun Team.towerIconRes(): Int = when (this) {
    Team.BLACK -> R.drawable.tower_black
    Team.WHITE -> R.drawable.tower_white
    Team.GREY -> R.drawable.tower_grey
}

@DrawableRes
fun assistantCardRes(cardId: Int): Int = when (cardId) {
    1 -> R.drawable.assistant_1
    2 -> R.drawable.assistant_2
    3 -> R.drawable.assistant_3
    4 -> R.drawable.assistant_4
    5 -> R.drawable.assistant_5
    6 -> R.drawable.assistant_6
    7 -> R.drawable.assistant_7
    8 -> R.drawable.assistant_8
    9 -> R.drawable.assistant_9
    else -> R.drawable.assistant_10
}
