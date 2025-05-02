package me.androidbox.scribbledash.gamemode.presentation

sealed interface FeedbackAction {
    data object OnRetry : FeedbackAction
}