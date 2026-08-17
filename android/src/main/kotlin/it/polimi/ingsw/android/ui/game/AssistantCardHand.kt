package it.polimi.ingsw.android.ui.game

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

/** Card id -> turn-order value shown on the card, mirroring GuiDrawer.drawCards's "[i, (i+1)/2]" format. */
private fun turnValueFor(cardId: Int) = (cardId + 1) / 2

@Composable
fun AssistantCardHand(
    deck: List<String>,
    onCardClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    val availableCardIds = (1..10).filter { cardId -> "[$cardId, ${turnValueFor(cardId)}]" in deck }

    LazyRow(modifier = modifier, horizontalArrangement = Arrangement.spacedBy(8.dp), contentPadding = PaddingValues(12.dp)) {
        items(availableCardIds) { cardId ->
            AssistantCard(cardId, onClick = { onCardClick(cardId) })
        }
    }
}

@Composable
private fun AssistantCard(cardId: Int, onClick: () -> Unit) {
    Image(
        painter = painterResource(assistantCardRes(cardId)),
        contentDescription = "Assistant card $cardId",
        modifier = Modifier
            .height(96.dp)
            .clip(RoundedCornerShape(8.dp))
            .clickable { onClick() },
    )
}
