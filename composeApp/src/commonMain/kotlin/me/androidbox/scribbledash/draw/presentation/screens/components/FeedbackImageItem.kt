package me.androidbox.scribbledash.draw.presentation.screens.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.fastForEach
import me.androidbox.scribbledash.draw.presentation.DrawingAction
import me.androidbox.scribbledash.draw.presentation.PaintPath
import me.androidbox.scribbledash.draw.presentation.VectorData
import kotlin.math.abs

@Composable
fun FeedbackImageItem(
    paths: List<PaintPath> = emptyList(),
    exampleToDrawPath: List<Path> = emptyList(),
    onAction: (DrawingAction) -> Unit,
    title: String,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.wrapContentWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = title,
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            lineHeight = 18.sp,
            color = MaterialTheme.colorScheme.onSurface
        )

        Spacer(modifier = Modifier.height(8.dp))

        if(exampleToDrawPath.isNotEmpty()) {
           DrawingCanvas(
                modifier = Modifier.size(160.dp),
                paths = emptyList(),
                currentPath = null,
                onAction = onAction,
                examplePath = exampleToDrawPath,
                vectorData = VectorData()
            )
        }
        else {
            Canvas(
                modifier = Modifier
                    .shadow(elevation = 4.dp, shape = MaterialTheme.shapes.large)
                    .size(160.dp)
                    .background(Color(0xFFFFFFFF))
                    .clip(RoundedCornerShape(18.dp))
                    .clipToBounds()
                    .drawBehind {
                        val cellSize = size.width / 3
                        for (i in 1..2) {
                            drawLine(
                                color = Color(0xFFF6F1EC),
                                start = Offset(i * cellSize, 0f),
                                end = Offset(i * cellSize, size.height),
                                strokeWidth = 1.dp.toPx(),
                                alpha = .7f
                            )
                            drawLine(
                                color = Color(0xFFF6F1EC),
                                start = Offset(0f, i * cellSize),
                                end = Offset(size.width, i * cellSize),
                                strokeWidth = 1.dp.toPx(),
                                alpha = .7f
                            )
                        }

                        val borderWidth = 2.dp.toPx()
                        val cornerRadius = 18.dp.toPx()

                        drawRoundRect(
                            color = Color(0xFFF6F1EC),
                            size = Size(size.width, size.height),
                            cornerRadius = CornerRadius(cornerRadius, cornerRadius),
                            style = Stroke(width = borderWidth)
                        )
                    }
            ) {
                // Define your viewport dimensions
                val viewportWidth = 1000f
                val viewportHeight = 1000f

                // Calculate scaling factors
                val canvasWidth = size.width
                val canvasHeight = size.height
                val scaleX = canvasWidth / viewportWidth
                val scaleY = canvasHeight / viewportHeight
                val scale = minOf(scaleX, scaleY)

                // Calculate translation to center the drawing
                val scaledWidth = viewportWidth * scale
                val scaledHeight = viewportHeight * scale
                val translateX = (canvasWidth - scaledWidth) / 2f
                val translateY = (canvasHeight - scaledHeight) / 2f

                // Apply transformation to all drawing operations
                withTransform({
                    translate(left = translateX, top = translateY)
                    scale(scaleX = scale, scaleY = scale, pivot = Offset.Zero)
                }) {
                    paths.fastForEach { pathData ->
                        drawOffsetPath(
                            path = pathData.path,
                            color = pathData.color
                        )
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