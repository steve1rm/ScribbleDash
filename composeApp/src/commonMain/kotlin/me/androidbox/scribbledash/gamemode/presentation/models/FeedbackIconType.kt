package me.androidbox.scribbledash.gamemode.presentation.models

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import me.androidbox.scribbledash.theming.success
import org.jetbrains.compose.resources.DrawableResource
import scribbledash.composeapp.generated.resources.Res
import scribbledash.composeapp.generated.resources.check
import scribbledash.composeapp.generated.resources.cross

enum class FeedbackIconType(
    val iconRes: DrawableResource,
    val background: @Composable () -> Color
) {

    CORRECT(
        iconRes = Res.drawable.check,
        background = { MaterialTheme.colorScheme.success }
    ),
    INCORRECT(
        iconRes = Res.drawable.cross,
        background = { MaterialTheme.colorScheme.error }
    )
}