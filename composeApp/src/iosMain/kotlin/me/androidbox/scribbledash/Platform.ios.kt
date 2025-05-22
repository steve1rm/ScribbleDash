package me.androidbox.scribbledash

import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.Path
import me.androidbox.scribbledash.core.presentation.utils.DifficultyLevelOptions
import me.androidbox.scribbledash.gamemode.data.SaveBitmapDrawing
import me.androidbox.scribbledash.gamemode.presentation.PaintPath
import me.androidbox.scribbledash.gamemode.presentation.ParsedPath
import me.androidbox.scribbledash.gamemode.presentation.utils.ParseXmlDrawable
import platform.UIKit.UIDevice

class IOSPlatform: Platform {
    override val name: String = UIDevice.currentDevice.systemName() + " " + UIDevice.currentDevice.systemVersion
}

actual fun getPlatform(): Platform = IOSPlatform()


actual class ParseXmlDrawableImp : ParseXmlDrawable {

    actual override fun parser(): List<Path> {
        TODO("Not yet implemented")
    }
}

actual suspend fun getResultScore(
    userPaths: List<PaintPath>,
    exampleParsedPath: ParsedPath,
    difficultyLevelOption: DifficultyLevelOptions
): Int {
    TODO("Not yet implemented")
}


actual class SaveBitmapDrawingImp : SaveBitmapDrawing {
    actual override suspend fun saveDrawing(
        bitmap: ImageBitmap
    ): String? {
        TODO()
    }
}