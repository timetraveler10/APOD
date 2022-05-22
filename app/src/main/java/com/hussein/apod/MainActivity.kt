package com.hussein.apod

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.hussein.apod.presentation.MainViewModel
import com.hussein.apod.presentation.NavGraphs
import com.hussein.apod.ui.theme.APODTheme
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.navigation.dependency
import com.ramcosta.composedestinations.utils.contains
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            APODTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    DestinationsNavHost(
                        navGraph = NavGraphs.root,
                        dependenciesContainerBuilder = { //this: DependenciesContainerBuilder<*>

                            // To tie SettingsViewModel to "settings" nested navigation graph,
                            // making it available to all screens that belong to it
                            if (NavGraphs.root.contains(destination)) {
                                val parentEntry = remember {
                                    navController.getBackStackEntry(NavGraphs.root.route)
                                }
                                dependency(hiltViewModel<MainViewModel>(parentEntry))
                            }

                            // To tie ActivityViewModel to the activity, making it available to all destinations
                            dependency(hiltViewModel<MainViewModel>(this@MainActivity))
                        })
                }
            }
        }
    }
}

