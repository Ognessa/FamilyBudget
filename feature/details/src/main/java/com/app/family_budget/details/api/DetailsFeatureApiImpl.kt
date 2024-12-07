package com.app.family_budget.details.api

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.app.family_budget.details.ui.DetailsScreen
import com.app.family_budget.details.ui.DetailsViewModel
import com.app.family_budget.details_api.DetailsFeatureApi
import com.app.ui.navigation.NavigationFactory

class DetailsFeatureApiImpl : DetailsFeatureApi, NavigationFactory {

    private val repoIdKey = "repoIdKey"
    private val route = "details"
    private val keyRoute = "$route/{$repoIdKey}"

    override fun route(repoId: Long): String = "$route/$repoId"

    override fun screen(builder: NavGraphBuilder, navController: NavHostController) {
        builder.composable(
            route = keyRoute,
            arguments = listOf(navArgument(repoIdKey) { type = NavType.LongType }),
        ) { navBackStackEntry ->

            val arguments = requireNotNull(navBackStackEntry.arguments)
            val repoIdArg = arguments.getLong(repoIdKey)

            DetailsScreen(
                viewModel = hiltViewModel<DetailsViewModel, DetailsViewModel.Factory> {
                    it.create(repoIdArg)
                }
            )
        }
    }
}
