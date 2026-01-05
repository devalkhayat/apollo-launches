package com.example.apollo_launches.ui.screens.details

import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.apollo_launches.domain.model.LaunchDetail
import com.example.apollo_launches.R
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LaunchDetailScreen(
    launchId: String,
    navController: NavHostController,
    viewModel: LaunchDetailViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(launchId) {
        viewModel.handleIntent(
            LaunchDetailIntent.LoadLaunch(launchId)
        )
    }

    Scaffold(

        topBar = { TopAppBar(
            navigationIcon = {

                IconButton(onClick = {
                    navController.popBackStack()
                }) {

                    Icon(
                        imageVector = Icons.Default.ArrowBack ,
                        contentDescription = stringResource(R.string.back)
                    )

                }
            },
            title = {
                Text(text = stringResource(R.string.details_title,launchId)) },
            ) }
    ) { paddingValues ->


        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {

            when {
                state.isLoading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }

                state.error != null -> {
                    ErrorContent(
                        message = state.error!!,
                        onRetry = {
                            viewModel.handleIntent(LaunchDetailIntent.Retry)
                        }
                    )
                }

                state.launch != null -> {
                    LaunchDetailContent(state.launch!!)
                }
            }
        }



    }

}


@Composable
private fun LaunchDetailContent(launch: LaunchDetail) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {

        Box(modifier = Modifier.weight(2f)) {
            launch.missionPatch?.let {
                AsyncImage(
                    model = it,
                    contentDescription = launch.missionName,
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(),
                    contentScale = ContentScale.Fit
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
        Box(modifier = Modifier.weight(1f)) {

           Column(modifier = Modifier.fillMaxHeight().verticalScroll(rememberScrollState())) {

               Text(
                   text = stringResource(R.string.rocket_title),
                   style = MaterialTheme.typography.headlineMedium
               )
               Text("Name: ${launch.rocketName}")
               Text("Type: ${launch.rocketType ?: "N/A"}")
               Text("ID: ${launch.rocketId ?: "N/A"}")

               /////////////
               Spacer(modifier = Modifier.height(12.dp))
               ////////////

               Text(
                   text = stringResource(R.string.mission_title),
                   style = MaterialTheme.typography.headlineMedium
               )
               Text("Name: ${launch.missionName}")


               Text(
                   text = stringResource(R.string.site_title),
                   style = MaterialTheme.typography.headlineMedium
               )
               Text("Name: ${launch.site}")


           }
        }



    }
}


@Composable
private fun ErrorContent(
    message: String,
    onRetry: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = message, color = MaterialTheme.colorScheme.error)
            Spacer(modifier = Modifier.height(12.dp))
            Button(onClick = onRetry) {
                Text( stringResource(
                    id = R.string.retry

                ))
            }
        }
    }
}

