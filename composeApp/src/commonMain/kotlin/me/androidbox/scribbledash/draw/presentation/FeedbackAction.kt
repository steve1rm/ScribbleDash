package me.androidbox.scribbledash.draw.presentation

sealed interface FeedbackAction {
    data object OnRetry : FeedbackAction
}