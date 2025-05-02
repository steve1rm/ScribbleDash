package me.androidbox.scribbledash.gamemode.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.getString
import scribbledash.composeapp.generated.resources.Res
import scribbledash.composeapp.generated.resources.feedback_good_1
import scribbledash.composeapp.generated.resources.feedback_good_10
import scribbledash.composeapp.generated.resources.feedback_good_2
import scribbledash.composeapp.generated.resources.feedback_good_3
import scribbledash.composeapp.generated.resources.feedback_good_4
import scribbledash.composeapp.generated.resources.feedback_good_5
import scribbledash.composeapp.generated.resources.feedback_good_6
import scribbledash.composeapp.generated.resources.feedback_good_7
import scribbledash.composeapp.generated.resources.feedback_good_8
import scribbledash.composeapp.generated.resources.feedback_good_9
import scribbledash.composeapp.generated.resources.feedback_oops_1
import scribbledash.composeapp.generated.resources.feedback_oops_10
import scribbledash.composeapp.generated.resources.feedback_oops_2
import scribbledash.composeapp.generated.resources.feedback_oops_3
import scribbledash.composeapp.generated.resources.feedback_oops_4
import scribbledash.composeapp.generated.resources.feedback_oops_5
import scribbledash.composeapp.generated.resources.feedback_oops_6
import scribbledash.composeapp.generated.resources.feedback_oops_7
import scribbledash.composeapp.generated.resources.feedback_oops_8
import scribbledash.composeapp.generated.resources.feedback_oops_9
import scribbledash.composeapp.generated.resources.feedback_woohoo_1
import scribbledash.composeapp.generated.resources.feedback_woohoo_10
import scribbledash.composeapp.generated.resources.feedback_woohoo_2
import scribbledash.composeapp.generated.resources.feedback_woohoo_3
import scribbledash.composeapp.generated.resources.feedback_woohoo_4
import scribbledash.composeapp.generated.resources.feedback_woohoo_5
import scribbledash.composeapp.generated.resources.feedback_woohoo_6
import scribbledash.composeapp.generated.resources.feedback_woohoo_7
import scribbledash.composeapp.generated.resources.feedback_woohoo_8
import scribbledash.composeapp.generated.resources.feedback_woohoo_9

class FeedbackViewModel : ViewModel() {

    val _feedbackState = MutableStateFlow(FeedbackState())
    val feedbackState = _feedbackState.asStateFlow()

    init {
        getRandomFeedbackResource(rate = (1..100).random())
    }

    val oopsFeedbackList = listOf(
        Res.string.feedback_oops_1,
        Res.string.feedback_oops_2,
        Res.string.feedback_oops_3,
        Res.string.feedback_oops_4,
        Res.string.feedback_oops_5,
        Res.string.feedback_oops_6,
        Res.string.feedback_oops_7,
        Res.string.feedback_oops_8,
        Res.string.feedback_oops_9,
        Res.string.feedback_oops_10,
    )

    val goodFeedbackList = listOf(
        Res.string.feedback_good_1,
        Res.string.feedback_good_2,
        Res.string.feedback_good_3,
        Res.string.feedback_good_4,
        Res.string.feedback_good_5,
        Res.string.feedback_good_6,
        Res.string.feedback_good_7,
        Res.string.feedback_good_8,
        Res.string.feedback_good_9,
        Res.string.feedback_good_10
    )

    val woohooFeedbackList = listOf(
        Res.string.feedback_woohoo_1,
        Res.string.feedback_woohoo_2,
        Res.string.feedback_woohoo_3,
        Res.string.feedback_woohoo_4,
        Res.string.feedback_woohoo_5,
        Res.string.feedback_woohoo_6,
        Res.string.feedback_woohoo_7,
        Res.string.feedback_woohoo_8,
        Res.string.feedback_woohoo_9,
        Res.string.feedback_woohoo_10,
    )

    fun onAction(action: FeedbackAction) {
        when(action) {
            FeedbackAction.OnRetry -> {
                /* no-op */
            }
        }
    }

    fun getRandomFeedbackResource(rate: Int) {
        viewModelScope.launch {
            val ratingArray = when (rate) {
                in 0..40 -> oopsFeedbackList
                in 40..90 -> goodFeedbackList
                in 100..100 -> woohooFeedbackList
                else -> null
            }

            val ratingTitle = when(rate) {
                in 0..40 -> "Oops"
                in 40..90 -> "Good"
                in 100..100 -> "Woohoo"
                else -> "Unknown"
            }

            val ratingText = ratingArray?.random() ?: Res.string.feedback_good_6

            _feedbackState.update { feedbackState ->
                feedbackState.copy(
                    ratingText = getString(ratingText),
                    ratingTitle = ratingTitle,
                    ratingPercent = rate
                )
            }
        }
    }
}