package com.app.family_budget

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import com.app.family_budget.list_api.ListFeatureApi
import com.app.ui.navigation.NavigationFactory
import com.app.ui.theme.ComposeBoilerplateTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var navigationFactories: @JvmSuppressWildcards Set<NavigationFactory>

    @Inject
    lateinit var listFeatureApi: ListFeatureApi

    private var keepSplashScreen: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen().setKeepOnScreenCondition {
            keepSplashScreen
        }

        setContent {
            ComposeBoilerplateTheme {

                LaunchedEffect(Unit) {
                    withContext(Dispatchers.IO) {
                        keepSplashScreen = false
                    }
                }

                val navController = rememberNavController()

                Scaffold(modifier = Modifier.fillMaxSize()) {
                    AppNavGraph(
                        navController = navController,
                        navigationFactories = navigationFactories,
                        startDestination = listFeatureApi.route,
                        modifier = Modifier.fillMaxSize().padding(it),
                    )
                }
            }
        }
    }
}