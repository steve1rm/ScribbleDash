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
    data object OneRoundWonderScreen : Route

    @Serializable
    data object SpeedDrawScreen : Route

    @Serializable
    data class FinalFeedbackScreen(
        val drawingCount: Int
    ) : Route

    @Serializable
    data object EndlessModeScreen : Route

    @Serializable
    data object FeedbackEndlessModeScreen : Route

    @Serializable
    data object StatisticsScreen : Route

    @Serializable
    data object FeedbackScreen : Route

    // Graphs
    @Serializable
    data object DrawingGraph : Route
}

val Route.routeName: String
    get() = this::class.simpleName ?: this.toString()