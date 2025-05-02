package me.androidbox.scribbledash

import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.Path
import me.androidbox.scribbledash.core.presentation.utils.DifficultyLevelOptions
import me.androidbox.scribbledash.gamemode.data.SaveBitmapDrawing
import me.androidbox.scribbledash.gamemode.presentation.PaintPath
import me.androidbox.scribbledash.gamemode.presentation.ParsedPath
import me.androidbox.scribbledash.gamemode.presentation.utils.ParseXmlDrawable

expect fun getPlatform(): Platform

expect class ParseXmlDrawableImp : ParseXmlDrawable {
    override fun parser(): List<Path>
}

expect class SaveBitmapDrawingImp : SaveBitmapDrawing {
    override suspend fun saveDrawing(
        bitmap: ImageBitmap
    ): String?
}

expect suspend fun getResultScore(
    userPaths: List<PaintPath>,
    exampleParsedPath: ParsedPath,
    difficultyLevelOption: DifficultyLevelOptions
): Int

interface Platform {
    val name: String
}
