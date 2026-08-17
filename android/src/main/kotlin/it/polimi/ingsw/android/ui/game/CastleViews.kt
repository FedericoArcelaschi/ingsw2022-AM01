package it.polimi.ingsw.android.ui.game

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import it.polimi.ingsw.android.R
import it.polimi.ingsw.communication.modelData.CastleData
import it.polimi.ingsw.server.model.baseLogic.StudentColor

private const val TOTAL_TOWERS_3_PLAYER = 6
private const val TOTAL_TOWERS_DEFAULT = 8

private fun totalTowersFor(nPlayer: Int) = if (nPlayer == 3) TOTAL_TOWERS_3_PLAYER else TOTAL_TOWERS_DEFAULT

@Composable
fun MyCastleView(
    castle: CastleData,
    nPlayer: Int,
    selectedIndices: Set<Int>,
    onStudentClick: (Int) -> Unit,
    onConfirmMoveToDiningRoom: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier) {
        Image(
            painter = painterResource(R.drawable.castle_bg),
            contentDescription = null,
            modifier = Modifier.fillMaxWidth(),
            contentScale = ContentScale.FillWidth,
        )
        Column(modifier = Modifier.padding(12.dp)) {
            Text("My castle (${castle.username()})", style = MaterialTheme.typography.titleMedium)

            Text("Towers", style = MaterialTheme.typography.labelMedium, modifier = Modifier.padding(top = 8.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                repeat(totalTowersFor(nPlayer) - castle.nTower()) {
                    TowerIcon(castle.towerColor())
                }
            }

            Text("Professors", style = MaterialTheme.typography.labelMedium, modifier = Modifier.padding(top = 12.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                StudentColor.values().forEach { color ->
                    TeacherSlot(color, owned = castle.teachers()[color] == castle.towerColor())
                }
            }

            Text("Waiting room", style = MaterialTheme.typography.labelMedium, modifier = Modifier.padding(top = 12.dp))
            FlowRow(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                castle.waitingRoom().forEachIndexed { index, color ->
                    StudentIcon(color, size = 28.dp, selected = index in selectedIndices, onClick = { onStudentClick(index) })
                }
            }
            Button(onClick = onConfirmMoveToDiningRoom, enabled = selectedIndices.isNotEmpty(), modifier = Modifier.padding(top = 8.dp)) {
                Text("Move to dining room")
            }

            Text("Dining room", style = MaterialTheme.typography.labelMedium, modifier = Modifier.padding(top = 12.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                castle.diningRoom().forEach { (color, count) ->
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        StudentIcon(color, size = 20.dp)
                        Text(" $count")
                    }
                }
            }

            Text(
                "Coins: ${castle.coins()}",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(top = 12.dp),
            )
            Text(
                castle.lastPlayedCard()?.let { "Last played card: $it" } ?: "No card played yet",
                style = MaterialTheme.typography.bodySmall,
            )
        }
    }
}

@Composable
fun OtherCastlesRow(
    castles: List<CastleData>,
    nPlayer: Int,
    modifier: Modifier = Modifier,
) {
    LazyRow(modifier = modifier, horizontalArrangement = Arrangement.spacedBy(8.dp), contentPadding = PaddingValues(12.dp)) {
        items(castles) { castle ->
            OtherCastleCard(castle, nPlayer)
        }
    }
}

@Composable
private fun OtherCastleCard(castle: CastleData, nPlayer: Int) {
    Card(
        modifier = Modifier.width(160.dp),
        colors = CardDefaults.cardColors(containerColor = castle.towerColor().toComposeColor().copy(alpha = 0.15f)),
    ) {
        Box {
            Image(
                painter = painterResource(R.drawable.castle_bg),
                contentDescription = null,
                modifier = Modifier.fillMaxWidth(),
                contentScale = ContentScale.FillWidth,
            )
            Column(modifier = Modifier.padding(8.dp)) {
                Text(castle.username(), style = MaterialTheme.typography.labelLarge)
                Row(verticalAlignment = Alignment.CenterVertically) {
                    TowerIcon(castle.towerColor(), size = 16.dp)
                    Text(" x${totalTowersFor(nPlayer) - castle.nTower()}", style = MaterialTheme.typography.labelSmall)
                }
                Row(
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    modifier = Modifier
                        .clip(RoundedCornerShape(4.dp))
                        .background(androidx.compose.ui.graphics.Color.White.copy(alpha = 0.6f)),
                ) {
                    castle.diningRoom().forEach { (color, count) ->
                        if (count > 0) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                StudentIcon(color, size = 14.dp)
                                Text(":$count", style = MaterialTheme.typography.labelSmall)
                            }
                        }
                    }
                }
                Text(
                    castle.lastPlayedCard()?.let { "Last: $it" } ?: "No card yet",
                    style = MaterialTheme.typography.labelSmall,
                )
            }
        }
    }
}
