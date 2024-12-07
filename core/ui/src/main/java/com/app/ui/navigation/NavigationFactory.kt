package com.app.ui.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController

interface NavigationFactory {
    fun screen(builder: NavGraphBuilder, navController: NavHostController)
}