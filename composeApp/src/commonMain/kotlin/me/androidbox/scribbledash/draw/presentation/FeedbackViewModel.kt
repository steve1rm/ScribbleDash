package me.androidbox.scribbledash.draw.presentation

import androidx.lifecycle.ViewModel

class FeedbackViewModel : ViewModel() {

    fun onAction(action: FeedbackAction) {
        when(action) {
            FeedbackAction.OnRetry -> {
                /* no-op */
            }
        }
    }
}