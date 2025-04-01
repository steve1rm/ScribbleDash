package me.androidbox.scribbledash

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform