package com.app.family_budget

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.app.ui.navigation.NavigationFactory

@Composable
fun AppNavGraph(
    modifier: Modifier,
    navController: NavHostController,
    navigationFactories: Set<NavigationFactory>,
    startDestination: String,
) {
    NavHost(
        navController = navController,
        modifier = modifier,
        startDestination = startDestination,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None },
    ) {
        navigationFactories.forEach {
            it.screen(this, navController)
        }
    }
}