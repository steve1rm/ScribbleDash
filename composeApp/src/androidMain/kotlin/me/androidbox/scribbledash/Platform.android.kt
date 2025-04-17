package me.androidbox.scribbledash

import android.content.Context
import android.os.Build
import me.androidbox.scribbledash.draw.presentation.utils.ParseXmlDrawable
import org.jetbrains.compose.resources.DrawableResource

class AndroidPlatform : Platform {
    override val name: String = "Android ${Build.VERSION.SDK_INT}"
}

actual fun getPlatform(): Platform = AndroidPlatform()

actual class ParseXmlDrawableImp(private val context: Context) : ParseXmlDrawable {
    actual override fun parser(drawableResource: DrawableResource) {
        TODO("Not yet implemented")
    }
}