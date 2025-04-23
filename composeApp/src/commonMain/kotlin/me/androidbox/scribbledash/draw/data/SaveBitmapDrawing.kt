package me.androidbox.scribbledash.draw.data

import androidx.compose.ui.graphics.ImageBitmap

interface SaveBitmapDrawing {
    suspend fun saveDrawing(bitmap: ImageBitmap): String?
}