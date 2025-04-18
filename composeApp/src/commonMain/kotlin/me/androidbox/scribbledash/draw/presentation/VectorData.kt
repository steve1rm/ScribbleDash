package me.androidbox.scribbledash.draw.presentation

data class VectorData(
    val viewportWidth: Float = 24f, // Default values
    val viewportHeight: Float = 24f,
    val paths: List<String> = emptyList()
)
