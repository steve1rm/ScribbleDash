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

    @Serializable
    data object StatisticsScreen : Route

    @Serializable
    data object FeedbackScreen : Route

    // Graphs
    @Serializable
    data object DrawingGraph : Route
}