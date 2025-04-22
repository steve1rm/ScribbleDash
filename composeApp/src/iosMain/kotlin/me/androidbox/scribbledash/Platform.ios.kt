package me.androidbox.scribbledash

import androidx.compose.ui.graphics.Path
import me.androidbox.scribbledash.draw.presentation.utils.ParseXmlDrawable
import platform.UIKit.UIDevice

class IOSPlatform: Platform {
    override val name: String = UIDevice.currentDevice.systemName() + " " + UIDevice.currentDevice.systemVersion
}

actual fun getPlatform(): Platform = IOSPlatform()

actual class ParseXmlDrawableImp : ParseXmlDrawable {
    actual override fun parser(drawableName: String): List<Path> {
        TODO("Not yet implemented")
    }
}