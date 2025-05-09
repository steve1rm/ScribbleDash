package me.androidbox.scribbledash.navigation

import kotlinx.serialization.Serializable
import me.androidbox.scribbledash.home.model.DifficultyLevelType
import me.androidbox.scribbledash.home.model.GameType

@Serializable
sealed interface Route {
    // Screens
    @Serializable
    data object HomeScreen : Route

    @Serializable
    data class DifficultyLevelScreen(
        val gameType: GameType
    ) : Route

    @Serializable
    data class OneRoundWonderScreen(
        val difficultyLevelType: DifficultyLevelType
    ) : Route

    @Serializable
    data class SpeedDrawScreen(
        val difficultyLevelType: DifficultyLevelType
    ) : Route

    @Serializable
    data class EndlessModeScreen(
        val difficultyLevelType: DifficultyLevelType
    ) : Route

    @Serializable
    data class FinalFeedbackScreen(
        val drawingCount: Int
    ) : Route

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