package com.app.ui.util

import android.content.Context
import dagger.hilt.android.EntryPointAccessors

fun <T> Context.inject(dependencyProvider: Class<T>): T {
    return EntryPointAccessors.fromApplication(
        this,
        dependencyProvider
    )
}