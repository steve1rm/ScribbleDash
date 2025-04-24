package me.androidbox.scribbledash.draw.presentation.screens.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import me.androidbox.scribbledash.draw.presentation.DrawingAction
import me.androidbox.scribbledash.draw.presentation.PaintPath
import me.androidbox.scribbledash.draw.presentation.VectorData

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

        DrawingCanvas(
            modifier = Modifier.size(160.dp),
            paths = paths,
            currentPath = null,
            onAction = onAction,
            examplePath = exampleToDrawPath,
            vectorData = VectorData()
        )
    }
}