package me.androidbox.scribbledash.draw.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.getStringArray
import scribbledash.composeapp.generated.resources.Res
import scribbledash.composeapp.generated.resources.good_array
import scribbledash.composeapp.generated.resources.oops_array
import scribbledash.composeapp.generated.resources.woohoo_array

class FeedbackViewModel : ViewModel() {

    val _feedbackState = MutableStateFlow(FeedbackState())
    val feedbackState = _feedbackState.asStateFlow()

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
                in 0..40 -> Res.array.oops_array
                in 40..70 -> Res.array.good_array
                in 70..79 -> Res.array.good_array
                in 80..90 -> Res.array.good_array
                in 100..100 -> Res.array.woohoo_array
                else -> null
            }

            val ratingText = if(ratingArray != null) {
                getStringArray(ratingArray).random()
            }
            else {
                "Unknown rating"
            }

            _feedbackState.update { feedbackState ->
                feedbackState.copy(
                    ratingText = ratingText
                )
            }
        }
    }
}