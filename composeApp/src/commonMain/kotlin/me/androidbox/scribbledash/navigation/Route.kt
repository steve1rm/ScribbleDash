package me.androidbox.scribbledash.navigation

import androidx.compose.ui.graphics.Path
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import me.androidbox.scribbledash.draw.presentation.PaintPath

@Serializable
sealed interface Route {
    // Screens
    @Serializable
    data object HomeScreen : Route

    @Serializable
    data object DifficultyLevelScreen : Route

    @Serializable
    data object DrawingScreen : Route

    @Serializable
    data object StatisticsScreen : Route

    @Serializable
    data object FeedbackScreen : Route

    // Graphs
    @Serializable
    data object DrawingGraph : Route
}