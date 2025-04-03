package me.androidbox.scribbledash.draw.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import me.androidbox.scribbledash.core.presentation.components.ScribbleDashLayout
import me.androidbox.scribbledash.draw.screens.components.DifficultyItem
import org.jetbrains.compose.resources.vectorResource
import scribbledash.composeapp.generated.resources.Res
import scribbledash.composeapp.generated.resources.brushes
import scribbledash.composeapp.generated.resources.close_circle
import scribbledash.composeapp.generated.resources.paints
import scribbledash.composeapp.generated.resources.pencil

@Composable
fun DrawingScreen(
    modifier: Modifier = Modifier
) {
    ScribbleDashLayout(
        modifier = modifier,
        toolBarTitle = "",
        navigationIcon = {
            Icon(imageVector = vectorResource(Res.drawable.close_circle),
                contentDescription = "Close the drawing screen")
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(paddingValues = paddingValues)
            ) {
                Text(
                    text = "Start drawing!",
                    style = MaterialTheme.typography.displayMedium,
                    color = MaterialTheme.colorScheme.onBackground
                )

                Text(
                    text = "Choose a difficulty setting",
                    style = MaterialTheme.typography.displayMedium,
                    color = MaterialTheme.colorScheme.onBackground
                )

                Spacer(modifier = Modifier.height(64.dp))

                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    DifficultyItem(
                        modifier = Modifier.weight(1f),
                        title = "Beginner",
                        image = {
                            Image(
                                imageVector = vectorResource(resource = Res.drawable.pencil),
                                contentDescription = "beginner difficulty"
                            )
                        }
                    )

                    DifficultyItem(
                        modifier = Modifier
                            .weight(1f)
                            .offset(
                                y = -(20).dp
                            ),
                        title = "Challenging",
                        image = {
                            Image(
                                imageVector = vectorResource(resource = Res.drawable.brushes),
                                contentDescription = "challenging difficulty"
                            )
                        }
                    )

                    DifficultyItem(
                        modifier = Modifier.weight(1f),
                        title = "Master",
                        image = {
                            Image(
                                imageVector = vectorResource(resource = Res.drawable.paints),
                                contentDescription = "master difficulty"
                            )
                        }
                    )
                }
            }
        }
    )
}

