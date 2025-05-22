package me.androidbox.scribbledash.gamemode.presentation.screens.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import me.androidbox.scribbledash.theming.ScribbleDashTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ResultTitleHeader(
    percent: String,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier
        .fillMaxWidth()) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(112.dp)
                .background(
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    shape = RoundedCornerShape(16.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "$percent%",
                style = MaterialTheme.typography.displayLarge,
                color = MaterialTheme.colorScheme.onBackground
            )
        }
    }
}

@Preview
@Composable
fun ResultTitleHeaderPreview() {
    val percent = "75"

    ScribbleDashTheme {
        ResultTitleHeader(
            modifier = Modifier.offset(
                y = -(20).dp
            ),
            percent = percent
        )
    }
}