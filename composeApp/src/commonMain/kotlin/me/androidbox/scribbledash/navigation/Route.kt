package me.androidbox.scribbledash.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface Route {
    // Screens
    @Serializable
    data object HomeScreen : Route

    @Serializable
    data object DifficultyLevelScreen : Route

    @Serializable
    data object DrawingScreen : Route

    // Graphs
    @Serializable
    data object DrawingGraph: Route
}