@file:OptIn(ExperimentalResourceApi::class, ExperimentalTime::class, ExperimentalTime::class)

package me.androidbox.scribbledash

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.RectF
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.graphics.asComposePath
import androidx.compose.ui.graphics.toArgb
import androidx.core.graphics.PathParser
import androidx.core.graphics.createBitmap
import androidx.core.graphics.toColorInt
import androidx.core.graphics.withSave
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.withContext
import me.androidbox.scribbledash.core.presentation.utils.DifficultyLevelOptions
import me.androidbox.scribbledash.draw.data.SaveBitmapDrawing
import me.androidbox.scribbledash.draw.presentation.PaintPath
import me.androidbox.scribbledash.draw.presentation.ParsedPath
import me.androidbox.scribbledash.draw.presentation.PathData
import me.androidbox.scribbledash.draw.presentation.utils.ParseXmlDrawable
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserException
import java.io.IOException
import kotlin.math.min
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

private fun calculateUserPathsBounds(paths: List<PaintPath>): RectF {
    val bounds = RectF()

    for (path in paths) {
        if (path.path.isNotEmpty()) {
            // Initialize with the first point
            val firstPoint = path.path.first()
            val pathBounds = RectF(firstPoint.x, firstPoint.y, firstPoint.x, firstPoint.y)

            // Expand with all other points
            for (point in path.path) {
                pathBounds.left = minOf(pathBounds.left, point.x)
                pathBounds.top = minOf(pathBounds.top, point.y)
                pathBounds.right = maxOf(pathBounds.right, point.x)
                pathBounds.bottom = maxOf(pathBounds.bottom, point.y)
            }

            // Expand by the stroke width
            pathBounds.inset(-path.strokeWidth / 2f, -path.strokeWidth / 2f)

            // Union with the overall bounds
            if (bounds.isEmpty) {
                bounds.set(pathBounds)
            } else {
                bounds.union(pathBounds)
            }
        }
    }

    return bounds
}

private fun calculateExamplePathsBounds(parsedPath: ParsedPath): RectF {
    val bounds = RectF()

    for (pathData in parsedPath.paths) {
        val path = PathParser.createPathFromPathData(pathData.pathData)
        if (path != null) {
            val pathBounds = RectF()
            path.computeBounds(pathBounds, true)

            // Expand by the stroke width
            pathBounds.inset(-pathData.strokeWidth / 2f, -pathData.strokeWidth / 2f)

            // Union with the overall bounds
            if (bounds.isEmpty) {
                bounds.set(pathBounds)
            } else {
                bounds.union(pathBounds)
            }
        }
    }

    return bounds
}


private fun normalizeAndDrawUserPaths(
    canvas: Canvas,
    paths: List<PaintPath>,
    bounds: RectF,
    canvasSize: Int
) {
    if (bounds.isEmpty) return

    canvas.withSave() {

        // Calculate scaling to fit the canvas while preserving aspect ratio
        val scaleX = canvasSize / bounds.width()
        val scaleY = canvasSize / bounds.height()
        val scale = min(scaleX, scaleY)

        // Center the drawing
        val scaledWidth = bounds.width() * scale
        val scaledHeight = bounds.height() * scale
        val translateX = (canvasSize - scaledWidth) / 2f - bounds.left * scale
        val translateY = (canvasSize - scaledHeight) / 2f - bounds.top * scale

        translate(translateX, translateY)
        scale(scale, scale)

        // Draw each path
        for (paintPath in paths) {
            if (paintPath.path.size > 1) {
                val paint = Paint().apply {
                    color = paintPath.color.toArgb()
                    strokeWidth = paintPath.strokeWidth
                    style = Paint.Style.STROKE
                    strokeCap = when (paintPath.strokeCap) {
                        StrokeCap.Round -> Paint.Cap.ROUND
                        StrokeCap.Square -> Paint.Cap.SQUARE
                        else -> Paint.Cap.BUTT
                    }
                    strokeJoin = when (paintPath.strokeJoin) {
                        StrokeJoin.Round -> Paint.Join.ROUND
                        StrokeJoin.Bevel -> Paint.Join.BEVEL
                        else -> Paint.Join.MITER
                    }
                    isAntiAlias = true
                }

                val path = android.graphics.Path()
                path.moveTo(paintPath.path.first().x, paintPath.path.first().y)

                for (i in 1 until paintPath.path.size) {
                    val from = paintPath.path[i - 1]
                    val to = paintPath.path[i]

                    if (i < paintPath.path.size - 1) {
                        val nextPoint = paintPath.path[i + 1]
                        val controlX = to.x
                        val controlY = to.y
                        val endX = (to.x + nextPoint.x) / 2
                        val endY = (to.y + nextPoint.y) / 2
                        path.quadTo(controlX, controlY, endX, endY)
                    } else {
                        path.lineTo(to.x, to.y)
                    }
                }

                drawPath(path, paint)
            }
        }

    }
}

actual suspend fun getResultScore(
    userPaths: List<PaintPath>,
    exampleParsedPath: ParsedPath,
    difficultyLevelOption: DifficultyLevelOptions
): Int {
    return withContext(Dispatchers.Default) {
        // Get stroke width multiplier based on difficulty
        val strokeWidthMultiplier = when (difficultyLevelOption) {
            DifficultyLevelOptions.Beginner -> 15f
            DifficultyLevelOptions.Challenging -> 7f
            DifficultyLevelOptions.Master -> 4f
        }

        // Get average stroke widths
        val userStrokeWidth = userPaths.map { it.strokeWidth }.average().toFloat()
        val exampleStrokeWidth =
            exampleParsedPath.paths.map { it.strokeWidth }.average().toFloat()

        // Step 1: Calculate bounds for both drawings
        val userBounds = calculateUserPathsBounds(userPaths)
        val exampleBounds = calculateExamplePathsBounds(exampleParsedPath)

        // Step 2a: Inset bounds by half the drawing's stroke width
        val userInsetBounds = RectF(userBounds).apply {
            val insetAmount = userStrokeWidth / 2f + (exampleStrokeWidth - userStrokeWidth) / 2f
            inset(insetAmount, insetAmount)
        }

        val exampleInsetBounds = RectF(exampleBounds).apply {
            inset(exampleStrokeWidth / 2f, exampleStrokeWidth / 2f)
        }

        // Step 3: Create bitmaps for rendering
        val canvasSize = 1000 // Use a fixed size for the comparison canvas

        val userBitmap = createBitmap(canvasSize, canvasSize)
        val exampleBitmap = createBitmap(canvasSize, canvasSize)

        val userCanvas = Canvas(userBitmap)
        val exampleCanvas = Canvas(exampleBitmap)

        // Clear with transparent
        userCanvas.drawColor(Color.Transparent.toArgb(), PorterDuff.Mode.CLEAR)
        exampleCanvas.drawColor(Color.Transparent.toArgb(), PorterDuff.Mode.CLEAR)

        // Step 2c & 2d: Scale and translate to normalize paths
        normalizeAndDrawUserPaths(userCanvas, userPaths, userInsetBounds, canvasSize)
        normalizeAndDrawExamplePaths(
            exampleCanvas,
            exampleParsedPath,
            exampleInsetBounds,
            canvasSize,
            strokeWidthMultiplier
        )

        // Step 4-7: Compare pixels
        var visibleUserPixelCount = 0
        var matchingUserPixelCount = 0

        val userPixels = IntArray(canvasSize * canvasSize)
        val examplePixels = IntArray(canvasSize * canvasSize)

        userBitmap.getPixels(userPixels, 0, canvasSize, 0, 0, canvasSize, canvasSize)
        exampleBitmap.getPixels(examplePixels, 0, canvasSize, 0, 0, canvasSize, canvasSize)

        for (i in userPixels.indices) {
            val userAlpha = android.graphics.Color.alpha(userPixels[i])
            val exampleAlpha = android.graphics.Color.alpha(examplePixels[i])

            if (userAlpha > 0) {
                visibleUserPixelCount++
                if (exampleAlpha > 0) {
                    matchingUserPixelCount++
                }
            }
        }

        // Step 8: Calculate score (avoid division by zero)
        val coveragePercentage = if (visibleUserPixelCount > 0) {
            (matchingUserPixelCount.toFloat() / visibleUserPixelCount.toFloat()) * 100
        } else {
            0f
        }

        // Clean up bitmaps to prevent memory leaks
        userBitmap.recycle()
        exampleBitmap.recycle()

        return@withContext coveragePercentage.toInt()
    }
}


private fun normalizeAndDrawExamplePaths(
    canvas: Canvas,
    parsedPath: ParsedPath,
    bounds: RectF,
    canvasSize: Int,
    strokeWidthMultiplier: Float
) {
    if (bounds.isEmpty) return

    canvas.withSave() {

        // Calculate scaling to fit the canvas while preserving aspect ratio
        val scaleX = canvasSize / bounds.width()
        val scaleY = canvasSize / bounds.height()
        val scale = min(scaleX, scaleY)

        // Center the drawing
        val scaledWidth = bounds.width() * scale
        val scaledHeight = bounds.height() * scale
        val translateX = (canvasSize - scaledWidth) / 2f - bounds.left * scale
        val translateY = (canvasSize - scaledHeight) / 2f - bounds.top * scale

        translate(translateX, translateY)
        scale(scale, scale)

        // Draw each path with thicker stroke
        for (pathData in parsedPath.paths) {
            val path = PathParser.createPathFromPathData(pathData.pathData)
            if (path != null) {
                val paint = Paint().apply {
                    try {
                        color = Color(pathData.strokeColor.toColorInt()).toArgb()
                    } catch (e: Exception) {
                        color = Color.Black.toArgb() // Fallback color
                    }
                    strokeWidth = pathData.strokeWidth * strokeWidthMultiplier
                    style = Paint.Style.STROKE
                    strokeCap = Paint.Cap.ROUND
                    strokeJoin = Paint.Join.ROUND
                    isAntiAlias = true
                }

                drawPath(path, paint)
            }
        }

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

    @SuppressLint("ResourceType")
    actual override fun parser(): List<Path> {

        val TAG = "VectorParse" // Tag for logging
        var viewportWidth = 24f // Default
        var viewportHeight = 24f // Default
        val pathDataList = mutableListOf<String>()
        val androidNamespace = "http://schemas.android.com/apk/res/android"
        var parser: XmlPullParser?

        var paths = emptyList<Path>()

        val listOfDrawings = listOf(
            R.drawable.alien,
            R.drawable.boat,
            R.drawable.book,
            R.drawable.camera,
            R.drawable.car,
            R.drawable.castle,
            R.drawable.cup,
            R.drawable.dog,
            R.drawable.envelope,
            R.drawable.eye,
            R.drawable.fish,
        )

        try {
            parser = context.resources.getXml(listOfDrawings.random())
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
            Log.e(TAG, "Drawable resource not found", ex)
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

private fun parseDimensionValue(context: Context, value: String): Int {
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