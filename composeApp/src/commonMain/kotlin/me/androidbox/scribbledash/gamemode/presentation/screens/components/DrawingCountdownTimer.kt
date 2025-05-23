package me.androidbox.scribbledash.gamemode.presentation.screens.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import me.androidbox.scribbledash.core.presentation.utils.toFormattedTime
import me.androidbox.scribbledash.theming.headLineXSmall
import kotlin.time.Duration

@Composable
fun DrawingCountdownTimer(
    duration: Duration,
    hasReachedFinalDuration: Boolean,
    modifier: Modifier = Modifier
) {

    Box(
        modifier = modifier
            .shadow(elevation = 8.dp, shape = CircleShape, ambientColor = Color.Black)
            .size(48.dp)
            .background(color = MaterialTheme.colorScheme.surfaceContainerHigh, shape = CircleShape),
        contentAlignment = Alignment.Center
    ) {

        Text(
            text = duration.toFormattedTime(),
            style = MaterialTheme.typography.headLineXSmall,
            color = if(hasReachedFinalDuration) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onBackground
        )
    }
}