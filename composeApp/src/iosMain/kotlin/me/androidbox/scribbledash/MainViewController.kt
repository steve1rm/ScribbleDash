package me.androidbox.scribbledash

import androidx.compose.ui.window.ComposeUIViewController

fun MainViewController() = ComposeUIViewController {

    initializeKoin()

    App()
}