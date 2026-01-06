package com.example.apollo_launches.ui.screens.launches

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.apollo_launches.ui.components.LaunchItem
import com.example.apollo_launches.ui.components.ThemeToggleButton
import com.example.apollo_launches.ui.theme.ThemeViewModel
import com.example.apollo_launches.R
import com.example.apollo_launches.domain.model.Launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LaunchListScreen(
    viewModel: LaunchViewModel = hiltViewModel(),
    themeViewModel: ThemeViewModel,
    onLaunchClick: (String) -> Unit
) {

    val state by viewModel.state.collectAsState()
    val lazyPagingItems = viewModel.launchesPagingFlow.collectAsLazyPagingItems()

    // Send ScreenOpened intent once
    LaunchedEffect(Unit) {
        viewModel.onIntent(LaunchIntent.ScreenOpened)
    }

    // Observe paging state and notify ViewModel (MVI)
    LaunchedEffect(lazyPagingItems.loadState) {
        when {
            lazyPagingItems.loadState.refresh is androidx.paging.LoadState.Loading -> {
                viewModel.onIntent(LaunchIntent.ScreenOpened)
            }

            lazyPagingItems.loadState.refresh is androidx.paging.LoadState.NotLoading -> {
                viewModel.onPagingLoaded()
            }

            lazyPagingItems.loadState.refresh is androidx.paging.LoadState.Error -> {
                val error =
                    (lazyPagingItems.loadState.refresh as androidx.paging.LoadState.Error)
                viewModel.onPagingError(
                    error.error.localizedMessage ?: "Unknown error"
                )
            }
        }
    }

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = { ScreenAppBar(scrollBehavior) }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {

            SummarySection(
                themeViewModel = themeViewModel,
                lazyPagingItems = lazyPagingItems,
                isLoading = state.isLoading
            )

            state.errorMessage?.let {
                Text(
                    text = it,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(8.dp)
                )
            }

            LaunchesSection(
                lazyPagingItems = lazyPagingItems,
                onLaunchClick = onLaunchClick
            )
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScreenAppBar(scrollBehavior: TopAppBarScrollBehavior){


    LargeTopAppBar(
        title = { Text( stringResource(
            id = R.string.space_launches

        )) },
        scrollBehavior = scrollBehavior
    )

}

@Composable
fun SummarySection(
    themeViewModel: ThemeViewModel,
    lazyPagingItems: LazyPagingItems<Launch>,
    isLoading: Boolean
) {
    Column(modifier = Modifier.fillMaxWidth()) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            ThemeToggleButton(themeViewModel)
        }

        if (isLoading) {
            Text(
                text = stringResource(R.string.loading_launches),
                modifier = Modifier.padding(8.dp)
            )
        }

        val loadedItemCount = lazyPagingItems.itemCount
        if (loadedItemCount > 0) {
            Text(
                text = stringResource(
                    R.string.launches_loaded_count,
                    loadedItemCount
                ),
                modifier = Modifier.padding(8.dp),
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Composable
fun LaunchesSection(lazyPagingItems: LazyPagingItems<Launch>,onLaunchClick: (String) -> Unit){

    // Launch list
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        items(lazyPagingItems.itemCount) { index ->
            val launch = lazyPagingItems[index]
            launch?.let {
                LaunchItem(
                    launch = it,
                    onClick = { onLaunchClick(it.id) }
                )
            }
        }

        lazyPagingItems.apply {
            when (loadState.append) {
                is androidx.paging.LoadState.Loading -> {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                }

                is androidx.paging.LoadState.Error -> {
                    val error = loadState.append as androidx.paging.LoadState.Error
                    item {
                        Text(
                            text = error.error.localizedMessage ?: "Error loading more",
                            color = MaterialTheme.colorScheme.error,
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                }

                else -> Unit
            }
        }
    }

}




