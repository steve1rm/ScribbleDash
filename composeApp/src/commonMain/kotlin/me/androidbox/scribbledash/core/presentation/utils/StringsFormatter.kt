package me.androidbox.scribbledash.core.presentation.utils

import kotlin.time.Duration

/** pad a 0 to the start i.e. 4:9 => 04:09 */
fun Long.padZero(): String {
    return this.toString().padStart(2, '0')
}

fun Duration.toFormattedTime(): String {
    val seconds = this.inWholeSeconds % 60

    return "${this.inWholeMinutes}:${seconds.padZero()}"
}