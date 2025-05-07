@file:OptIn(ExperimentalMaterial3Api::class)

package me.androidbox.scribbledash.gamemode.presentation.screens

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import me.androidbox.scribbledash.core.presentation.components.ScribbleDashLayout
import me.androidbox.scribbledash.gamemode.presentation.FeedbackAction
import me.androidbox.scribbledash.gamemode.presentation.FeedbackState
import me.androidbox.scribbledash.gamemode.presentation.PaintPath
import me.androidbox.scribbledash.gamemode.presentation.screens.components.FeedbackIcon
import me.androidbox.scribbledash.gamemode.presentation.screens.components.FeedbackImageItem
import me.androidbox.scribbledash.theming.success
import org.jetbrains.compose.resources.vectorResource
import scribbledash.composeapp.generated.resources.Res
import scribbledash.composeapp.generated.resources.close_circle

@Composable
fun FeedbackEndlessModeScreen(
    paths: List<PaintPath> = emptyList(),
    exampleToDrawPath: List<Path> = listOf(),
    feedbackState: FeedbackState,
    onAction: (FeedbackAction) -> Unit,
    closeClicked: () -> Unit,
    modifier: Modifier = Modifier
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
                            .clickable(onClick = closeClicked),
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
                    .fillMaxWidth()
                    .padding(paddingValues = paddingValues)
                    .padding(horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(128.dp))

                Text(
                    text = "${feedbackState.ratingPercent}%",
                    style = MaterialTheme.typography.displayMedium,
                    color = MaterialTheme.colorScheme.onBackground
                )

                Spacer(modifier = Modifier.height(32.dp))

                Box(
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(24.dp, alignment = Alignment.CenterHorizontally)
                    ) {
                        FeedbackImageItem(
                            modifier = Modifier.graphicsLayer {
                                this.rotationZ = -10f
                            },
                            exampleToDrawPath = exampleToDrawPath,
                            title = "Example",
                            onAction = {}
                        )

                        FeedbackImageItem(
                            modifier = Modifier
                                .graphicsLayer {
                                    this.rotationZ = 10f
                                }
                                .offset(
                                    y = 30.dp
                                ),
                            paths = paths,
                            title = "Drawing",
                            onAction = {}
                        )
                    }

                    FeedbackIcon(
                        modifier = Modifier.align(Alignment.BottomCenter),
                        icon = feedbackState.feedbackIconType.iconRes,
                        background = feedbackState.feedbackIconType.background()
                    )
                }

                Spacer(modifier = Modifier.height(64.dp))

                Text(
                    text = feedbackState.ratingTitle,
                    style = MaterialTheme.typography.displayMedium,
                    color = MaterialTheme.colorScheme.onBackground
                )

                Spacer(modifier = Modifier.height(32.dp))

                Text(
                    text = feedbackState.ratingText,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onBackground
                )

                Spacer(modifier = Modifier.weight(1f))

                Button(
                    modifier = Modifier
                        .height(height = 64.dp)
                        .fillMaxWidth()
                        .border(
                            width = 8.dp,
                            color = MaterialTheme.colorScheme.surfaceContainerHigh,
                            shape = RoundedCornerShape(size = 20.dp)
                        ),
                    shape = RoundedCornerShape(20.dp),
                    elevation = ButtonDefaults.elevatedButtonElevation(2.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary),
                    onClick = {
                        onAction(FeedbackAction.OnRetry)
                    }
                ) {
                    Text(
                        text = "Finish".uppercase(),
                        style = MaterialTheme.typography.headlineSmall,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    modifier = Modifier
                        .height(height = 64.dp)
                        .fillMaxWidth()
                        .border(
                            width = 8.dp,
                            color = MaterialTheme.colorScheme.surfaceContainerHigh,
                            shape = RoundedCornerShape(size = 20.dp)
                        ),
                    shape = RoundedCornerShape(20.dp),
                    elevation = ButtonDefaults.elevatedButtonElevation(2.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.success),
                    onClick = {
                        onAction(FeedbackAction.OnRetry)
                    }
                ) {
                    Text(
                        text = "Next Drawing".uppercase(),
                        style = MaterialTheme.typography.headlineSmall,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    )
}