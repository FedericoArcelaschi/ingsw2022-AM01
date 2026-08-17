package it.polimi.ingsw.android.ui.game

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import it.polimi.ingsw.android.R
import it.polimi.ingsw.communication.modelData.CloudData

private val islandsBackdrop = Color(0xFFADD8E6)

@Composable
fun CloudsRow(
    clouds: List<CloudData>,
    onCloudClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyRow(
        modifier = modifier
            .fillMaxWidth()
            .background(islandsBackdrop),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        contentPadding = PaddingValues(12.dp),
    ) {
        items(clouds.size) { index ->
            CloudTile(clouds[index], onClick = { onCloudClick(index) })
        }
    }
}

@Composable
private fun CloudTile(cloud: CloudData, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .width(96.dp)
            .aspectRatio(1f)
            .clickable(enabled = cloud.available()) { onClick() },
        contentAlignment = Alignment.Center,
    ) {
        Image(
            painter = painterResource(R.drawable.cloud_card),
            contentDescription = if (cloud.available()) "Available cloud" else "Taken cloud",
            modifier = Modifier.fillMaxWidth(),
            contentScale = ContentScale.Fit,
            alpha = if (cloud.available()) 1f else 0.5f,
        )
        if (cloud.studentList().isNotEmpty()) {
            androidx.compose.foundation.layout.FlowRow(
                horizontalArrangement = Arrangement.spacedBy(2.dp),
                modifier = Modifier.width(56.dp),
            ) {
                cloud.studentList().forEach { color -> StudentIcon(color, size = 16.dp) }
            }
        } else if (!cloud.available()) {
            Text("taken")
        }
    }
}
