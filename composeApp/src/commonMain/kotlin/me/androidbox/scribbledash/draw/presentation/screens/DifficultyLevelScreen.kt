@file:OptIn(ExperimentalMaterial3Api::class)

package me.androidbox.scribbledash.draw.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import me.androidbox.scribbledash.core.presentation.components.ScribbleDashLayout
import me.androidbox.scribbledash.draw.presentation.screens.components.DifficultyItem
import org.jetbrains.compose.resources.vectorResource
import scribbledash.composeapp.generated.resources.Res
import scribbledash.composeapp.generated.resources.brushes
import scribbledash.composeapp.generated.resources.close_circle
import scribbledash.composeapp.generated.resources.paints
import scribbledash.composeapp.generated.resources.pencil

@Composable
fun DifficultyLevelScreen(
    modifier: Modifier = Modifier,
    closeClicked: () -> Unit,
    beginnerClicked: () -> Unit,
    challengingClicked: () -> Unit,
    masterClicked: () -> Unit
) {
    ScribbleDashLayout(
        modifier = modifier,
        toolBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                ),
                actions = {
                    IconButton(
                        onClick = closeClicked
                    ) {
                        Icon(
                            modifier = Modifier.size(32.dp),
                            imageVector = vectorResource(Res.drawable.close_circle),
                            contentDescription = "Close this screen",
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    }
                },
                title = {}
            )
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(paddingValues = paddingValues),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(90.dp))

                Text(
                    text = "Start drawing!",
                    style = MaterialTheme.typography.displayMedium,
                    color = MaterialTheme.colorScheme.onBackground
                )

                Text(
                    text = "Choose a difficulty setting",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onBackground
                )

                Spacer(modifier = Modifier.height(64.dp))

                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    DifficultyItem(
                        modifier = Modifier
                            .weight(1f)
                            .clickable(
                                onClick = beginnerClicked
                            ),
                        title = "Beginner",
                        image = {
                            Image(
                                modifier = Modifier.shadow(
                                    elevation = 4.dp,
                                    shape = CircleShape),
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
                            )
                            .clickable(
                                onClick = challengingClicked
                            ),
                        title = "Challenging",
                        image = {
                            Image(
                                modifier = Modifier.shadow(elevation = 4.dp, shape = CircleShape),
                                imageVector = vectorResource(resource = Res.drawable.brushes),
                                contentDescription = "challenging difficulty"
                            )
                        }
                    )

                    DifficultyItem(
                        modifier = Modifier
                            .weight(1f)
                            .clickable(
                                onClick = masterClicked
                            ),
                        title = "Master",
                        image = {
                            Image(
                                modifier = Modifier.shadow(elevation = 4.dp, shape = CircleShape),
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

