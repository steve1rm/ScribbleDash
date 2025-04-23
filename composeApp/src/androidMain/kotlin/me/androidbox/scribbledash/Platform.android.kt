@file:OptIn(ExperimentalResourceApi::class, ExperimentalTime::class)

package me.androidbox.scribbledash

import android.content.ContentValues
import android.content.Context
import android.content.res.Resources
import android.graphics.Bitmap
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.graphics.asComposePath
import androidx.core.graphics.PathParser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.withContext
import me.androidbox.scribbledash.draw.data.SaveBitmapDrawing
import me.androidbox.scribbledash.draw.presentation.ParsedPath
import me.androidbox.scribbledash.draw.presentation.PathData
import me.androidbox.scribbledash.draw.presentation.utils.ParseXmlDrawable
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserException
import java.io.IOException
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

class AndroidPlatform : Platform {
    override val name: String = "Android ${Build.VERSION.SDK_INT}"
}

actual fun getPlatform(): Platform = AndroidPlatform()

actual class SaveBitmapDrawingImp(private val context: Context) : SaveBitmapDrawing {
    actual override suspend fun saveDrawing(
        bitmap: ImageBitmap
    ): String? {
        val fileName = "drawing_${Clock.System.now()}"

        val contentValues = ContentValues().apply {
            this.put(MediaStore.Images.Media.DISPLAY_NAME, fileName)
            this.put(MediaStore.Images.Media.MIME_TYPE, "image/webp")
            this.put(MediaStore.Images.Media.RELATIVE_PATH, Environment.DIRECTORY_PICTURES + "/scribbleDraw")
        }

        val path = withContext(Dispatchers.IO) {
            try {
                val resolver = context.contentResolver
                val uri = resolver.insert(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)

                if(uri != null) {
                    resolver.openOutputStream(uri).use { outputStream ->
                        if(outputStream != null) {
                            bitmap.asAndroidBitmap()
                                .compress(Bitmap.CompressFormat.WEBP, 100, outputStream)
                        }
                        else {
                            throw IllegalStateException("Failed to open output stream")
                        }
                    }

                    uri.path
                }
                else {
                    throw IllegalAccessException("Failed to insert bitmap")
                }
            }
            catch (exception: Exception) {
                exception.printStackTrace()
                coroutineContext.ensureActive()
                throw IllegalStateException("Failed to save bitmap ${exception.message}")
            }
        }

        return path
    }
}

private fun parseDimensionValue(value: String, context: Context): Int {
    if (value.isEmpty()) return 0

    try {
        if (value.startsWith("@")) {
            val resourceId = value.substring(1).toIntOrNull() ?: 0
            return context.resources.getDimensionPixelSize(resourceId)
        }

        if (value.all { it.isDigit() }) {
            return value.toInt()
        }

        val numericPart = value.takeWhile { it.isDigit() || it == '.' }
        val unit = value.substring(numericPart.length)

        val numericValue = numericPart.toFloatOrNull() ?: return 0

        return when (unit) {
            "dp", "dip" -> (numericValue * context.resources.displayMetrics.density).toInt()
            "sp" -> (numericValue * context.resources.displayMetrics.scaledDensity).toInt()
            "px" -> numericValue.toInt()
            "in" -> (numericValue * context.resources.displayMetrics.xdpi).toInt()
            "mm" -> (numericValue * context.resources.displayMetrics.xdpi / 25.4f).toInt()
            "pt" -> (numericValue * context.resources.displayMetrics.xdpi / 72f).toInt()
            "" -> numericValue.toInt() // Assume pixels if no unit specified
            else -> 0 // Unknown unit
        }
    } catch (e: Exception) {
        e.printStackTrace()
        return 0
    }
}


actual class ParseXmlDrawableImp(private val context: Context) : ParseXmlDrawable {

    fun getPathData(drawableResId: Int): ParsedPath {
        val parser = context.resources.getXml(drawableResId)

        val paths = mutableListOf<PathData>()
        var width = 0
        var height = 0
        var viewportWidth = 0f
        var viewportHeight = 0f

        try {
            var eventType = parser.eventType

            while (eventType != XmlPullParser.END_DOCUMENT) {
                when (eventType) {
                    XmlPullParser.START_TAG -> {
                        when (parser.name) {
                            "vector" -> {
                                // Parse vector attributes
                                for (i in 0 until parser.attributeCount) {
                                    val attrName = parser.getAttributeName(i)
                                    val attrValue = parser.getAttributeValue(i)

                                    when (attrName) {
                                        "width" -> width = parseDimensionValue(attrValue, context)
                                        "height" -> height = parseDimensionValue(attrValue,context)
                                        "viewportWidth" -> viewportWidth = attrValue.toFloat()
                                        "viewportHeight" -> viewportHeight = attrValue.toFloat()
                                    }
                                }
                            }

                            "path" -> {
                                // Parse path attributes
                                var pathData = ""
                                var strokeWidth = 0f
                                var fillColor = ""
                                var strokeColor = ""

                                for (i in 0 until parser.attributeCount) {
                                    val attrName = parser.getAttributeName(i)
                                    val attrValue = parser.getAttributeValue(i)

                                    when (attrName) {
                                        "pathData" -> pathData = attrValue
                                        "strokeWidth" -> strokeWidth = attrValue.toFloat()
                                        "fillColor" -> fillColor = attrValue
                                        "strokeColor" -> strokeColor = attrValue
                                    }
                                }

                                paths.add(
                                    PathData(
                                        pathData,
                                        strokeWidth,
                                        fillColor,
                                        strokeColor
                                    )
                                )
                            }
                        }
                    }
                }
                eventType = parser.next()
            }

        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            parser.close()
        }
        return ParsedPath(
            paths = paths,
            width = width,
            height = height,
            viewportWidth = viewportWidth,
            viewportHeight = viewportHeight
        )
    }

    actual override fun parser(drawableName: String): List<Path> {

        val TAG = "VectorParse" // Tag for logging
        var viewportWidth = 24f // Default
        var viewportHeight = 24f // Default
        val pathDataList = mutableListOf<String>()
        val androidNamespace = "http://schemas.android.com/apk/res/android"
        var parser: XmlPullParser?

        var paths = emptyList<Path>()

        try {
            val id = context.resources.getIdentifier(drawableName, "drawable", context.packageName)

            parser = context.resources.getXml(id)
            var eventType = parser.eventType

            while (eventType != XmlPullParser.END_DOCUMENT) {
                when (eventType) {
                    XmlPullParser.START_TAG -> {
                        val tagName = parser.name // Get tag name once

                        when (tagName) {
                            "vector" -> {
                                val vwStr = parser.getAttributeValue(androidNamespace, "viewportWidth")
                                val vhStr = parser.getAttributeValue(androidNamespace, "viewportHeight")

                                viewportWidth = vwStr?.toFloatOrNull() ?: viewportWidth
                                viewportHeight = vhStr?.toFloatOrNull() ?: viewportHeight
                                Log.d(TAG, "Found <vector>: viewportWidth=$viewportWidth ($vwStr), viewportHeight=$viewportHeight ($vhStr)")
                            }
                            "path" -> {
                                val pathData = parser.getAttributeValue(androidNamespace, "pathData")

                                if (!pathData.isNullOrBlank()) {
                                    pathDataList.add(pathData)
                                } else {
                                    Log.w(TAG, "Found <path> tag with null or blank pathData.")
                                }
                            }
                        }
                    }
                }
                eventType = parser.next()
            }
            Log.d(TAG, "Parsing finished. Found ${pathDataList.size} paths.")

            paths = pathDataList.mapNotNull { pathData ->
                PathParser.createPathFromPathData(pathData).asComposePath()
            }
        } catch (ex: Resources.NotFoundException) {
            Log.e(TAG, "Drawable resource not found: $drawableName", ex)
        } catch (ex: XmlPullParserException) {
            Log.e(TAG, "XML parsing error", ex)
        } catch (ex: IOException) {
            Log.e(TAG, "XML reading IO error", ex)
        } catch (ex: NumberFormatException) {
            Log.e(TAG, "Error parsing float for viewport dimensions", ex)
        } catch (ex: Exception) { // Catch general exceptions during parsing
            Log.e(TAG, "Unexpected error during parsing", ex)
        }

        return paths
    }
}