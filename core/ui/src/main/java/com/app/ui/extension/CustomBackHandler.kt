package com.app.ui.extension

import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect

@Composable
fun CustomBackHandler(onBackPressed: () -> Unit) {
    val dispatcherOwner = LocalOnBackPressedDispatcherOwner.current
    val dispatcher = dispatcherOwner?.onBackPressedDispatcher

    val backCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            onBackPressed()
        }
    }

    DisposableEffect(dispatcher) {
        dispatcher?.addCallback(backCallback)
        onDispose {
            backCallback.remove()
        }
    }
}