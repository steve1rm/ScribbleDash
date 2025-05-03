@file:OptIn(ExperimentalMaterial3Api::class)

package me.androidbox.scribbledash.gamemode.presentation.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import me.androidbox.scribbledash.core.presentation.components.ScribbleDashLayout
import org.jetbrains.compose.resources.vectorResource
import scribbledash.composeapp.generated.resources.Res
import scribbledash.composeapp.generated.resources.close_circle

@Composable
fun FeedbackSpeedDrawScreen(
    modifier: Modifier = Modifier,
    onCloseClickedk: () -> Unit
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
                            .clickable(onClick = onCloseClickedk),
                        imageVector = vectorResource(Res.drawable.close_circle),
                        contentDescription = "Close the feedback screen",
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                }
            )
        },
        content = { paddingValues ->
            Spacer(modifier = Modifier.height(128.dp))

            Text(
                text = "Time's up!",
                style = MaterialTheme.typography.displayMedium,
                color = MaterialTheme.colorScheme.onBackground
            )
        }
    )
}

