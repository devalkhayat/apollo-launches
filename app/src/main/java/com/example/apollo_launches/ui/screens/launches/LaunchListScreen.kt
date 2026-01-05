package com.example.apollo_launches.ui.screens.launches

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.apollo_launches.domain.model.Launch

import com.example.apollo_launches.ui.components.LaunchItem

import com.example.apollo_launches.ui.components.ThemeToggleButton
import com.example.apollo_launches.ui.theme.ThemeViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.example.apollo_launches.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LaunchListScreen(
    viewModel: LaunchViewModel = hiltViewModel(),
    themeViewModel: ThemeViewModel,
    onLaunchClick: (String) -> Unit
) {
    val currentTheme by themeViewModel.theme.collectAsState()
    val lazyPagingItems = viewModel.launchesPagingFlow.collectAsLazyPagingItems()

    // TopAppBar scroll behavior
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        topBar = {
            LargeTopAppBar(
                title = { Text( stringResource(
                    id = R.string.space_launches

                )) },
                scrollBehavior = scrollBehavior
            )
        },
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection)
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {

            Row(modifier = Modifier.fillMaxWidth()){
                ThemeToggleButton(themeViewModel)

                // Show loaded count
                val loadedItemCount = lazyPagingItems.itemCount
                if (loadedItemCount > 0) {
                    Text(
                        text = stringResource(
                            id = R.string.launches_loaded_count,
                            loadedItemCount
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }



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

                // Bottom loading indicator
                lazyPagingItems.apply {
                    when {
                        loadState.append is androidx.paging.LoadState.Loading -> {
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

                        loadState.append is androidx.paging.LoadState.Error -> {
                            val e = lazyPagingItems.loadState.append as androidx.paging.LoadState.Error
                            item {
                                Text(
                                    text = "Error: ${e.error.localizedMessage ?: "Unknown"}",
                                    color = MaterialTheme.colorScheme.error,
                                    modifier = Modifier.padding(16.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}




