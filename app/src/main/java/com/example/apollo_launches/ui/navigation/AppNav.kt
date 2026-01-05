package com.example.apollo_launches.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.apollo_launches.ui.screens.details.LaunchDetailScreen
import com.example.apollo_launches.ui.screens.details.LaunchDetailViewModel

import com.example.apollo_launches.ui.screens.launches.LaunchListScreen
import com.example.apollo_launches.ui.screens.launches.LaunchViewModel
import com.example.apollo_launches.ui.theme.ThemeViewModel


sealed class Screen(val route: String) {
    object LaunchList : Screen("launch_list")
    object LaunchDetail : Screen("launch_detail/{launchId}") {
        fun createRoute(launchId: String) = "launch_detail/$launchId"
    }
}

@Composable
fun AppNavGraph(navController: NavHostController,modifier: Modifier = Modifier,themeViewModel: ThemeViewModel) {
    NavHost(
        navController = navController,
        startDestination = Screen.LaunchList.route,
        modifier = modifier
    ) {

        composable(Screen.LaunchList.route) {
            val viewModel: LaunchViewModel = hiltViewModel()

            LaunchListScreen(
                viewModel = viewModel ,
                themeViewModel = themeViewModel,
                onLaunchClick = { launchId ->
                    navController.navigate(
                        Screen.LaunchDetail.createRoute(launchId)
                    )
                } ,
            )
        }

        composable(
            route = Screen.LaunchDetail.route,
            arguments = listOf(
                navArgument("launchId") {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->

            val launchId =
                backStackEntry.arguments?.getString("launchId")
                    ?: return@composable

            val viewModel: LaunchDetailViewModel = hiltViewModel()

            LaunchDetailScreen(
                launchId = launchId,
                viewModel = viewModel,
                navController = navController
            )
        }
    }
}

