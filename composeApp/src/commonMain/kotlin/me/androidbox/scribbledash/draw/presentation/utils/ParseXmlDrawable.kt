package me.androidbox.scribbledash.draw.presentation.utils

import androidx.compose.ui.graphics.Path

interface ParseXmlDrawable {
    fun parser(drawableName: String): List<Path>
}