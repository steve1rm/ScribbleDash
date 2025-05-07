package me.androidbox.scribbledash.gamemode.presentation

import me.androidbox.scribbledash.gamemode.presentation.models.FeedbackIconType

data class FeedbackState(
    val ratingPercent: Int = 0,
    val ratingTitle: String = "",
    val ratingText: String = "",
    val feedbackIconType: FeedbackIconType = FeedbackIconType.INCORRECT
)
