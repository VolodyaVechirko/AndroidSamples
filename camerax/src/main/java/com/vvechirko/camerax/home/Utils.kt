package com.vvechirko.camerax.home

import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.os.Build
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.graphics.painter.Painter
import java.io.File

// Should be async
@Composable
fun painterFile(filePath: String): Painter {
    val imageBitmap = if (Build.VERSION.SDK_INT < 28) {
        val option = BitmapFactory.Options().apply {
            inJustDecodeBounds = false
            inSampleSize = 4
        }
        BitmapFactory.decodeFile(filePath, option)
    } else {
        val source = ImageDecoder.createSource(File(filePath))
        ImageDecoder.decodeBitmap(source) { decoder, info, src ->
            Log.d("App", "ImageDecoder info: ${info.size}")
            decoder.setTargetSampleSize(4)
        }
    }
    return BitmapPainter(imageBitmap.asImageBitmap())
}