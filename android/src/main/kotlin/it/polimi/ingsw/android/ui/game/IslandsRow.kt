package it.polimi.ingsw.android.ui.game

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import it.polimi.ingsw.android.R
import it.polimi.ingsw.communication.modelData.IslandData

private val islandsBackdrop = Color(0xFFADD8E6) // matches desktop's #islandsPane { -fx-background-color: lightblue; }

@Composable
fun IslandsRow(
    islands: List<IslandData>,
    motherNaturePosition: Int,
    onIslandClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyRow(
        modifier = modifier
            .fillMaxWidth()
            .background(islandsBackdrop),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        contentPadding = PaddingValues(12.dp),
    ) {
        items(islands.size) { index ->
            IslandTile(
                island = islands[index],
                islandNumber = index + 1,
                hasMotherNature = index == motherNaturePosition,
                onClick = { onIslandClick(index) },
            )
        }
    }
}

@Composable
private fun IslandTile(island: IslandData, islandNumber: Int, hasMotherNature: Boolean, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .width(104.dp)
            .aspectRatio(1f)
            .clickable { onClick() },
        contentAlignment = Alignment.Center,
    ) {
        Image(
            painter = painterResource(if (island.isBlocked()) R.drawable.island_blocked else R.drawable.island_bg),
            contentDescription = "Island $islandNumber",
            modifier = Modifier.fillMaxWidth(),
            contentScale = ContentScale.Fit,
        )
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("#$islandNumber", style = MaterialTheme.typography.labelMedium)
            if (hasMotherNature) MotherNatureIcon(size = 22.dp)

            island.students().forEach { (color, count) ->
                if (count > 0) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        StudentIcon(color, size = 12.dp)
                        Text(" $count", style = MaterialTheme.typography.labelSmall)
                    }
                }
            }

            island.ownership()?.let { owner ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .clip(RoundedCornerShape(4.dp))
                        .background(Color.White.copy(alpha = 0.6f)),
                ) {
                    TowerIcon(owner, size = 14.dp)
                    Text(" x${island.islandSize()}", style = MaterialTheme.typography.labelSmall)
                }
            }
        }
    }
}
