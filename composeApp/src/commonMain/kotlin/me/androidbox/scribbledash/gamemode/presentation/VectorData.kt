package me.androidbox.scribbledash.gamemode.presentation

data class VectorData(
    val viewportWidth: Float = 100f, // Default values
    val viewportHeight: Float = 100f,
    val paths: List<String> = emptyList()
)
