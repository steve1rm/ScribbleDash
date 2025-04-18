package me.androidbox.scribbledash.statistics.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import me.androidbox.scribbledash.core.presentation.components.ScribbleDashLayout
import me.androidbox.scribbledash.statistics.presentation.components.StatisticsItem
import me.androidbox.scribbledash.theming.labelXLarge
import org.jetbrains.compose.resources.vectorResource
import scribbledash.composeapp.generated.resources.Res
import scribbledash.composeapp.generated.resources.eggtimer

@Composable
fun StatisticsScreen(
    modifier: Modifier = Modifier
) {
    ScribbleDashLayout(
        modifier = modifier,
        toolBar = {
            Text(
                text = "Statistics",
                style = MaterialTheme.typography.labelXLarge,
                color = MaterialTheme.colorScheme.onBackground
            )
        },
        content = { paddingValues ->

        }
    )
}