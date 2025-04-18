package me.androidbox.scribbledash.draw.presentation.screens.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.graphics.vector.PathParser
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEach
import me.androidbox.scribbledash.draw.presentation.DrawingAction
import me.androidbox.scribbledash.draw.presentation.PathData
import me.androidbox.scribbledash.draw.presentation.VectorData
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

@Composable
fun DrawingCanvas(
    paths: List<PathData>,
    currentPath: PathData?,
    samplePath: List<Path>,
    vectorData: VectorData,
    onAction: (DrawingAction) -> Unit,
    modifier: Modifier = Modifier) {

    Box(
        modifier = modifier
            .size(size = 360.dp)
            .shadow(elevation = 4.dp, shape = MaterialTheme.shapes.large)
            .background(
                color = MaterialTheme.colorScheme.surfaceContainerHigh,
                shape = RoundedCornerShape(20.dp)
            ),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .size(330.dp)
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    shape = RoundedCornerShape(20.dp)),
        ) {
            Canvas(
                modifier = Modifier
                    .fillMaxSize()
                    .clipToBounds()
                    .pointerInput(
                        key1 = true,
                        block = {
                            this.detectDragGestures(
                                onDragStart = {
                                    onAction(DrawingAction.OnNewPathStart)
                                    println("onDragStart ${currentPath?.path}")
                                },
                                onDragEnd = {
                                    onAction(DrawingAction.OnPathEnd)
                                    println("onDragEnd ${currentPath?.path}")
                                },
                                onDrag = { change, _ ->
                                    onAction(DrawingAction.OnDraw(change.position))
                                },
                                onDragCancel = {
                                    onAction(DrawingAction.OnPathEnd)
                                }
                            )
                        })) {
                val stokeWidth = 1.dp.toPx()

                // Vertical Lines
                val x1 = size.width / 3f
                val x2 = 2 * size.width / 3f

                drawLine(
                    color = Color(0xffF6F1EC),
                    start = Offset(x = x1, y = 0f),
                    end = Offset(x = x1, y = size.height),
                    strokeWidth = stokeWidth
                )

                drawLine(
                    color = Color(0xffF6F1EC),
                    start = Offset(x = x2, y = 0f),
                    end = Offset(x = x2, y = size.height),
                    strokeWidth = stokeWidth
                )

                // Horizontal Lines
                val y1 = size.height / 3f
                val y2 = 2 * size.height / 3f

                drawLine(
                    color = Color(0xffF6F1EC),
                    start = Offset(x = 0f, y = y1),
                    end = Offset(x = size.width, y = y1),
                    strokeWidth = stokeWidth
                )

                drawLine(
                    color = Color(0xffF6F1EC),
                    start = Offset(x = 0f, y = y2),
                    end = Offset(x = size.width, y = y2),
                    strokeWidth = stokeWidth
                )

                // Draw paths
                paths.fastForEach { pathData ->
                    drawPath(
                        path = pathData.path,
                        color = pathData.color
                    )
                }

                currentPath?.let { pathData ->
                    drawPath(
                        path = pathData.path,
                        color = pathData.color
                    )
                }

                if (samplePath.isNotEmpty() && vectorData.viewportWidth > 0 && vectorData.viewportHeight > 0) {
                    val canvasWidth = size.width
                    val canvasHeight = size.height

                    // Calculate scale factors
                    val scaleX = canvasWidth / vectorData.viewportWidth
                    val scaleY = canvasHeight / vectorData.viewportHeight

                    // Use the smaller scale factor to fit entire image (maintaining aspect ratio)
                    val scale = max(scaleX, scaleY)

                    // Calculate translation to center the scaled image
                    val scaledWidth = vectorData.viewportWidth * scale
                    val scaledHeight = vectorData.viewportHeight * scale
                    val translateX = (canvasWidth - scaledWidth) / 2f
                    val translateY = (canvasHeight - scaledHeight) / 2f

                    // Apply transformations: first translate, then scale
                    // Origin for scaling is top-left (0,0) by default
                   /* withTransform({
                        translate(left = translateX, top = translateY)
                        scale(scaleX = scale, scaleY = scale, pivot = Offset.Zero)
                    }) {*/
                        samplePath.forEachIndexed { index, path ->

                            drawPath(
                                path = path,
                                color = Color.Red, // Bright color
                                // Use a large width in the original coordinate space
                                style = Stroke(width = 1f) // Try 5f, or even 10f relative to 100x100
                            )
                 //       }
                    }
                }
            }
        }
    }
}

private fun DrawScope.drawPath(
    path: List<Offset>,
    color: Color,
    thickness: Float = 10F
) {
    val smoothedPath = Path().apply {
        if(path.isNotEmpty()) {
            moveTo(path.first().x, path.first().y)

            val smoothness = 5
            for(i in 1..path.lastIndex) {
                val from = path[i - 1]
                val to = path[i]
                val dx = abs(from.x - to.x)
                val dy = abs(from.y - to.y)
                if(dx >= smoothness || dy >= smoothness) {
                    quadraticTo(
                        x1 = (from.x + to.x) / 2f,
                        y1 = (from.y + to.y) / 2f,
                        x2 = to.x,
                        y2 = to.y
                    )
                }
            }
        }
    }

    drawPath(
        path = smoothedPath,
        color = color,
        style = Stroke(
            width = thickness,
            cap = StrokeCap.Round,
            join = StrokeJoin.Round
        )
    )
}