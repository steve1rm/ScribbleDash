package me.androidbox.scribbledash.gamemode.presentation.screens.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import me.androidbox.scribbledash.gamemode.presentation.models.FeedbackIconType
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.vectorResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun FeedbackIcon(
    modifier: Modifier = Modifier,
    icon: DrawableResource,
    background: Color
) {
    Box(
        modifier = modifier
            .size(72.dp)
            .background(color = background, shape = RoundedCornerShape(100))
            .border(width = 6.dp, color = Color.White, shape = RoundedCornerShape(100)),
        contentAlignment = Alignment.Center,
    ) {
        Icon(
            modifier = Modifier.size(36.dp),
            imageVector = vectorResource(icon),
            contentDescription = null,
            tint = Color.White
        )
    }
}

@Preview
@Composable
fun FeedbackIconPreview() {
    FeedbackIcon(
        icon = FeedbackIconType.CORRECT.iconRes,
        background = FeedbackIconType.CORRECT.background())
}