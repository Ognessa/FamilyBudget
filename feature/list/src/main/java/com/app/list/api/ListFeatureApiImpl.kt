package com.app.list.api

import android.content.Context
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.app.family_budget.list_api.ListFeatureApi
import com.app.list.di.DependencyProvider
import com.app.list.ui.ListScreen
import com.app.ui.navigation.NavigationFactory
import com.app.ui.navigation.navigateSingle
import com.app.ui.util.inject

class ListFeatureApiImpl(
    context: Context
) : ListFeatureApi, NavigationFactory {

    private val di = context.inject(DependencyProvider::class.java)

    override val route: String = "list_graph"
    private val listDestination = "list"

    override fun screen(builder: NavGraphBuilder, navController: NavHostController) {
        builder.navigation(route = route, startDestination = listDestination) {
            composable(listDestination) {

                ListScreen(
                    openDetailsScreen = { repo ->
                        navController.navigateSingle(di.detailsFeatureApi.route(repo.id))
                    },
                )
            }
        }
    }
}