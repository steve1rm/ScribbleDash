@file:OptIn(ExperimentalMaterial3Api::class)

package me.androidbox.scribbledash.gamemode.presentation.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import me.androidbox.scribbledash.core.presentation.components.ScribbleDashLayout
import me.androidbox.scribbledash.gamemode.presentation.screens.components.SpeedDrawFeedbackCard
import me.androidbox.scribbledash.theming.labelXLarge
import me.androidbox.scribbledash.theming.onBackgroundVariant
import org.jetbrains.compose.resources.vectorResource
import scribbledash.composeapp.generated.resources.Res
import scribbledash.composeapp.generated.resources.close_circle

@Composable
fun FeedbackSpeedDrawScreen(
    drawingCount: Int,
    modifier: Modifier = Modifier,
    onCloseClicked: () -> Unit
) {
    ScribbleDashLayout(
        modifier = modifier,
        toolBar = {
            TopAppBar(
                title = {},
                actions = {
                    Icon(
                        modifier = Modifier
                            .size(32.dp)
                            .clickable(onClick = onCloseClicked),
                        imageVector = vectorResource(Res.drawable.close_circle),
                        contentDescription = "Close the feedback screen",
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                }
            )
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .padding(paddingValues = paddingValues)
                    .padding(horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Time's up!",
                    style = MaterialTheme.typography.labelXLarge,
                    color = MaterialTheme.colorScheme.onBackgroundVariant
                )

                Spacer(modifier = Modifier.height(32.dp))

                SpeedDrawFeedbackCard(
                    percent = "80",
                    rating = "Woohoo!",
                    description = "You've officially raised the bar!\nI'm going to need a ladder to reach it!\"",
                    drawingCount = drawingCount.toString()
                )
            }
        }
    )
}

