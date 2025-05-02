package me.androidbox.scribbledash.gamemode.presentation.utils

import androidx.compose.ui.graphics.Path

interface ParseXmlDrawable {
    fun parser(): List<Path>
}