package me.androidbox.scribbledash.theming

import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

val lightPrimary = Color(0xff238CFF)
val lightSecondary = Color(0xffAB5CFA)
val lightTertiary = Color(0xffFA852C)
val error = Color(0xffEF1242)
val success = Color(0xff0DD280)
val lightOnPrimary = Color(0xffffffff)
val lightBackground = Color(0xffFEFAF6)
val lightOnBackground = Color(0xff514437)
val lightOnBackgroundVariant = Color(0xff7F7163)
val lightOnSurface = Color(0xffA5978A)
val lightOnSurfaceVariant = Color(0xffF6F1EC)
val lightSurfaceLow = Color(0xffEEE7E0)
val lightSurfaceLowest = Color(0xffE1D5CA)
val lightSurfaceHigh = Color(0xffffffff)

internal val lightColorScheme = lightColorScheme(
    primary = lightPrimary,
    background = lightBackground,
    onBackground = lightOnBackground,
    onSurface = lightOnSurface,
    secondary = lightSecondary,
    surfaceContainerHigh = lightSurfaceHigh,
    surfaceContainerLowest = lightSurfaceLowest
)

internal val darkColorScheme = lightColorScheme(
    primary = lightPrimary,
    background = lightBackground,
    onBackground = lightOnBackground,
    onSurface = lightOnSurface,
    secondary = lightSecondary,
)