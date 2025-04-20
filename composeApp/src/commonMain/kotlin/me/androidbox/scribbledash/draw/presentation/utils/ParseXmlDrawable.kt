package me.androidbox.scribbledash.draw.presentation.utils

import androidx.compose.ui.graphics.Path
import org.jetbrains.compose.resources.DrawableResource

interface ParseXmlDrawable {
    fun parser(drawableId: DrawableResource): List<Path>
}