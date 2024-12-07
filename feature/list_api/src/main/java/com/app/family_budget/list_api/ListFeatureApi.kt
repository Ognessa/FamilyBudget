package com.app.family_budget.list_api

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController

interface ListFeatureApi {
    val route: String
    fun screen(builder: NavGraphBuilder, navController: NavHostController)
}