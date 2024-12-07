package com.app.ui.navigation

import androidx.annotation.MainThread
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavController
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.navOptions

@MainThread
fun NavController.navigateSingle(route: String, builder: (NavOptionsBuilder.() -> Unit)? = null) {
    navigate(route, navOptions {
        launchSingleTop = true
        builder?.invoke(this)
    })
}

@MainThread
fun NavController.safePopBackStack(): Boolean {
    return if (currentBackStackEntry?.lifecycle?.currentState == Lifecycle.State.RESUMED) {
        popBackStack()
    } else false
}