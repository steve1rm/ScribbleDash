package me.androidbox.scribbledash.draw.screens.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontVariation.width
import androidx.compose.ui.unit.dp

@Composable
fun DrawingCanvas(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .size(size = 360.dp)
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
                modifier = Modifier.fillMaxSize()) {
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
            }
        }
    }
}