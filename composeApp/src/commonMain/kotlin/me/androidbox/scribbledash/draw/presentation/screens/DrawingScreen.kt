@file:OptIn(ExperimentalMaterial3Api::class)

package me.androidbox.scribbledash.draw.presentation.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import me.androidbox.scribbledash.draw.presentation.DrawingAction
import me.androidbox.scribbledash.draw.presentation.DrawingState
import me.androidbox.scribbledash.draw.presentation.VectorData
import me.androidbox.scribbledash.draw.presentation.screens.components.DrawControls
import me.androidbox.scribbledash.draw.presentation.screens.components.DrawingCanvas
import org.jetbrains.compose.resources.vectorResource
import scribbledash.composeapp.generated.resources.Res
import scribbledash.composeapp.generated.resources.close_circle

@Composable
fun DrawingScreen(
    drawingState: DrawingState,
    onAction: (DrawingAction) -> Unit,
    closeClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    ScribbleDashLayout(
        modifier = modifier.fillMaxSize(),
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
                    .padding(paddingValues = paddingValues)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(120.dp))

                Text(
                    text = "Time to draw!",
                    style = MaterialTheme.typography.displayMedium,
                    color = MaterialTheme.colorScheme.onBackground
                )

                Spacer(modifier = Modifier.height(16.dp))

                DrawingCanvas(
                    paths = drawingState.paths,
                    currentPath = drawingState.currentPath,
                    onAction = onAction,
                    samplePath = drawingState.samplePath,
                    vectorData = VectorData()
                )

                Spacer(modifier = Modifier.weight(1f))

                DrawControls(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 32.dp),
                    clearEnabled = drawingState.paths.isNotEmpty(),
                    unDoEnabled = drawingState.paths.isNotEmpty(),
                    redoEnabled = drawingState.undonePaths.isNotEmpty(),
                    onUndoClicked = {
                        onAction(DrawingAction.Undo)
                    },
                    onRedoClicked = {
                        onAction(DrawingAction.Redo)
                    },
                    onClearClicked = {
                        onAction(DrawingAction.OnClearCanvasClicked)
                    },
                )
            }
        }
    )
}