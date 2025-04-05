@file:OptIn(ExperimentalMaterial3Api::class)

package me.androidbox.scribbledash.draw.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.unit.dp
import me.androidbox.scribbledash.core.presentation.components.ScribbleDashLayout
import me.androidbox.scribbledash.draw.screens.components.DrawControls
import org.jetbrains.compose.resources.vectorResource
import scribbledash.composeapp.generated.resources.Res
import scribbledash.composeapp.generated.resources.close_circle

@Composable
fun CanvasScreen(
    modifier: Modifier = Modifier,
    closeClicked: () -> Unit
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
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                },
                title = {}
            )
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(120.dp))

                Text(
                    text = "Time to draw!",
                    style = MaterialTheme.typography.displayMedium,
                    color = MaterialTheme.colorScheme.onBackground
                )

                DrawControls(
                    onUndoClicked = {},
                    onRedoClicked = {},
                    onClearClicked = {}
                )
            }
        }
    )
}