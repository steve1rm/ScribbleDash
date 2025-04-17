package me.androidbox.scribbledash

import me.androidbox.scribbledash.draw.presentation.utils.ParseXmlDrawable
import org.jetbrains.compose.resources.DrawableResource
import platform.UIKit.UIDevice

class IOSPlatform: Platform {
    override val name: String = UIDevice.currentDevice.systemName() + " " + UIDevice.currentDevice.systemVersion
}

actual fun getPlatform(): Platform = IOSPlatform()

actual class ParseXmlDrawableImp : ParseXmlDrawable {
    actual override fun parser(drawableResource: DrawableResource) {
        TODO("Not yet implemented")
    }
}