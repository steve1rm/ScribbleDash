package me.androidbox.scribbledash

import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.Path
import me.androidbox.scribbledash.draw.data.SaveBitmapDrawing
import me.androidbox.scribbledash.draw.presentation.utils.ParseXmlDrawable

expect fun getPlatform(): Platform

expect class ParseXmlDrawableImp : ParseXmlDrawable {
    override fun parser(drawableName: String): List<Path>
}

expect class SaveBitmapDrawingImp : SaveBitmapDrawing {
    override suspend fun saveDrawing(
        bitmap: ImageBitmap
    ): String?
}

interface Platform {
    val name: String
}
