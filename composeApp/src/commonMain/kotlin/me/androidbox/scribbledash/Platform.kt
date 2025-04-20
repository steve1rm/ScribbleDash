package me.androidbox.scribbledash

import androidx.compose.ui.graphics.Path
import me.androidbox.scribbledash.draw.presentation.utils.ParseXmlDrawable
import org.jetbrains.compose.resources.DrawableResource

expect fun getPlatform(): Platform

expect class ParseXmlDrawableImp : ParseXmlDrawable {
    override fun parser(drawableId: DrawableResource): List<Path>
}

interface Platform {
    val name: String
}
