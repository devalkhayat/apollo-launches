package com.example.apollo_launches

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.apollo_launches.ui.navigation.AppNavGraph
import com.example.apollo_launches.ui.theme.ApolloLaunchesTheme
import com.example.apollo_launches.ui.theme.ThemeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MainApp()
        }
    }
}

@Composable
fun MainApp(themeViewModel: ThemeViewModel = viewModel()){

    val currentTheme by themeViewModel.theme.collectAsState()

    ApolloLaunchesTheme(theme = currentTheme) {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            AppNavGraph(navController = rememberNavController(),modifier = Modifier.padding(innerPadding),themeViewModel)
        }
    }
}



@Preview(showBackground = true)
@Composable
fun GreetingPreview() {

}