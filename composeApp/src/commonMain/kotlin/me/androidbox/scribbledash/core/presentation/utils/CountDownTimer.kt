@file:OptIn(ExperimentalCoroutinesApi::class)

package me.androidbox.scribbledash.core.presentation.utils

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

fun countDownTimer(initialTime: Duration, isPaused: StateFlow<Boolean> = MutableStateFlow(false)): Flow<Duration> {
    return flow {
        var timeRemaining = initialTime

        while (timeRemaining > Duration.ZERO) {
            if (!isPaused.value) {
                emit(timeRemaining)
                delay(1.seconds)
                timeRemaining -= 1.seconds
            }
            else {
                delay(100L)
            }
        }

        emit(Duration.ZERO)
    }
}