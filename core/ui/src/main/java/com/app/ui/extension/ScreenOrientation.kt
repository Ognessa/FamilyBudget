package com.app.ui.extension

import android.annotation.SuppressLint
import android.app.Activity
import android.content.pm.ActivityInfo

fun Activity.isLandscape(): Boolean {
    return requestedOrientation == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
}

@SuppressLint("SourceLockedOrientationActivity")
fun Activity.rotateScreen() {
    requestedOrientation = if (isLandscape()) {
        showSystemBars()
        ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    } else {
        hideSystemBars()
        ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
    }
}

@SuppressLint("SourceLockedOrientationActivity")
fun Activity.setDefaultOrientation() {
    requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
}