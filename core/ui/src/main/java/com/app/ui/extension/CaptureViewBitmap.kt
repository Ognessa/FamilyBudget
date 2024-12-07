package com.app.ui.extension

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Rect
import android.view.PixelCopy
import android.view.View
import com.app.ui.util.findActivity

fun View.captureViewBitmap(callback: (Bitmap?) -> Unit) {
    val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)

    val rect = Rect(0, 0, width, height)
    val bitmapResult = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
    val locationOfViewInWindow = IntArray(2)

    getLocationInWindow(locationOfViewInWindow)

    val source = Rect(
        locationOfViewInWindow[0],
        locationOfViewInWindow[1],
        locationOfViewInWindow[0] + width,
        locationOfViewInWindow[1] + height
    )

    try {
        PixelCopy.request(context.findActivity().window, source, bitmapResult, { copyResult ->
            if (copyResult == PixelCopy.SUCCESS) {
                val canvas = Canvas(bitmap)
                canvas.drawColor(Color.WHITE)
                canvas.drawBitmap(bitmapResult, rect, rect, null)
                callback(bitmap)
            } else {
                callback(null)
            }
        }, handler)
    } catch (e: IllegalArgumentException) {
        callback(null)
    }
}