package me.androidbox.scribbledash.theming

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable

@Composable
fun ScribbleDashTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if(!darkTheme) lightColorScheme else lightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = appTypography(),
        shapes = Shapes,
        content = content
    )
}