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
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEach
import me.androidbox.scribbledash.draw.presentation.DrawingAction
import me.androidbox.scribbledash.draw.presentation.PaintPath
import me.androidbox.scribbledash.draw.presentation.PathData
import me.androidbox.scribbledash.draw.presentation.VectorData
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

@Composable
fun DrawingCanvas(
    paths: List<PaintPath>,
    currentPath: PaintPath?,
    examplePath: List<Path>,
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
                    drawOffsetPath(
                        path = pathData.path,
                        color = pathData.color
                    )
                }

                currentPath?.let { pathData ->
                    drawOffsetPath(
                        path = pathData.path,
                        color = pathData.color
                    )
                }

                if (examplePath.isNotEmpty() && vectorData.viewportWidth > 0 && vectorData.viewportHeight > 0) {
                    val canvasWidth = this.size.width
                    val canvasHeight = this.size.height

                    // Calculate scale factors
                    val scaleX = canvasWidth / vectorData.viewportWidth
                    val scaleY = canvasHeight / vectorData.viewportHeight

                    println("Before scaling:")
                    println("Canvas W/H: $canvasWidth / $canvasHeight")
                    println("Viewport W/H: ${vectorData.viewportWidth} / ${vectorData.viewportHeight}")
                    println("Scale X/Y: $scaleX / $scaleY")

                    println("Canvas W/H: $canvasWidth / $canvasHeight")
                    println("Vector VP W/H: ${vectorData.viewportWidth} / ${vectorData.viewportHeight}")
                    println("Scale X/Y: $scaleX / $scaleY")
                    val scale = min(scaleX, scaleY)
                    println("Chosen Scale (max): $scale")  // Note: We're using max now
                    val scaledWidth = vectorData.viewportWidth * scale
                    val scaledHeight = vectorData.viewportHeight * scale
                    println("Scaled W/H: $scaledWidth / $scaledHeight")
                    val translateX = (canvasWidth - scaledWidth) / 2f
                    val translateY = (canvasHeight - scaledHeight) / 2f
                    println("Translate X/Y: $translateX / $translateY")

                    withTransform({
                        translate(left = translateX, top = translateY)
                        scale(scaleX = scale, scaleY = scale, pivot = Offset.Zero)
                    }) {
                        println("Inside withTransform - Drawing sample paths...")
                        examplePath.forEach { path ->
                            val bounds = path.getBounds() // Get the bounding box
                            println("Drawing sample path Bounds: $bounds")

                            // Only draw if the path has non-zero dimensions
                            if (!bounds.isEmpty) {
                                drawPath(
                                    path = path,
                                    color = Color.Black,
                                    style = Stroke(width = 1f)
                                )

                            } else {
                                println("--> Path bounds are empty. Skipping draw.")
                            }
                        }
                    }
                }
            }
        }
    }
}

private fun DrawScope.drawOffsetPath(
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
