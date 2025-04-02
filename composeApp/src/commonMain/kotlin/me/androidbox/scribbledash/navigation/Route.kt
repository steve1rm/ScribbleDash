package me.androidbox.scribbledash.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface Route {
    @Serializable
    data object HomeScreen : Route

    @Serializable
    data object DrawingScreen : Route

    @Serializable
    data object FunScreen : Route
}