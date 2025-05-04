package me.androidbox.scribbledash.core.presentation.utils

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

fun countDownTimer(initialTime: Duration, isPaused: Boolean = false): Flow<Duration> {
    return flow {
        var timeRemaining = initialTime

        if (!isPaused) {
            while (timeRemaining > Duration.ZERO) {
                emit(timeRemaining)
                delay(1.seconds)
                timeRemaining -= 1.seconds
            }

            emit(Duration.ZERO)
        }
    }
}