package me.androidbox.scribbledash

import me.androidbox.scribbledash.draw.presentation.utils.ParseXmlDrawable
import org.jetbrains.compose.resources.DrawableResource

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform

expect class ParseXmlDrawableImp : ParseXmlDrawable {
    override fun parser(drawableResource: DrawableResource)
}