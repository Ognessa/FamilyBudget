package com.app.family_budget.details_api

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController

interface DetailsFeatureApi {
    fun route(repoId: Long): String
    fun screen(builder: NavGraphBuilder, navController: NavHostController)
}