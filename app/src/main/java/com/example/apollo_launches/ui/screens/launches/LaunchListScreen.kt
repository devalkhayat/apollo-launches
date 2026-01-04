package com.example.apollo_launches.ui.screens.launches


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.example.apollo_launches.R
import com.example.apollo_launches.domain.model.LaunchSummary
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@Composable
fun LaunchListScreen(
    viewModel: LaunchListViewModel,
    onLaunchClick: (String) -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.handleIntent(LaunchListIntent.LoadLaunches)
    }

    Box(modifier = Modifier.fillMaxSize()) {

        when {
            state.isLoading && state.launches.isEmpty() -> {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            state.error != null && state.launches.isEmpty() -> {
                ErrorView(
                    message = state.error,
                    onRetry = {
                        viewModel.handleIntent(LaunchListIntent.LoadLaunches)
                    }
                )
            }

            else -> {
                LaunchListContent(
                    launches = state.launches,
                    hasMore = state.hasMore,
                    onLaunchClick = onLaunchClick,
                    onLoadMore = {
                        viewModel.handleIntent(
                            LaunchListIntent.LoadNextPage(pageSize = 20)
                        )
                    }
                )
            }
        }

        if (state.isLoading && state.launches.isNotEmpty()) {
            LinearProgressIndicator(
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}
@Composable
private fun LaunchListContent(
    launches: List<LaunchSummary>,
    hasMore: Boolean,
    onLaunchClick: (String) -> Unit,
    onLoadMore: () -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        itemsIndexed(
            items = launches,
            key = { _, item -> item.id }
        ) { index, launch ->

            LaunchItem(
                launch = launch,
                onClick = {
                    onLaunchClick(launch.id)
                }
            )

            // Trigger pagination near bottom
            if (hasMore && index == launches.lastIndex - 2) {
                LaunchedEffect(Unit) {
                    onLoadMore()
                }
            }
        }

        if (hasMore) {
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
    }
}
@Composable
private fun LaunchItem(
    launch: LaunchSummary,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            // ✅ Mission patch image
            AsyncImage(
                model = launch.missionPatch ,
                contentDescription = launch.missionName ,
                modifier = Modifier
                    .size(64.dp)
                    .clip(RoundedCornerShape(8.dp)) ,
                contentScale = ContentScale.Crop ,
                placeholder = painterResource(R.drawable.ic_placeholder) ,
                error = painterResource(R.drawable.ic_placeholder)
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {

                launch.missionName?.let {
                    Text(
                        text = it,
                        style = MaterialTheme.typography.titleMedium
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "Launch ID: ${launch.id}",
                    style = MaterialTheme.typography.bodyMedium
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = launch.rocketName,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}



@Composable
fun ErrorView(
    message: String?,
    onRetry: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = message ?: "Something went wrong",
            style = MaterialTheme.typography.bodyMedium
        )

        Spacer(modifier = Modifier.height(12.dp))

        Button(onClick = onRetry) {
            Text("Retry")
        }
    }
}



