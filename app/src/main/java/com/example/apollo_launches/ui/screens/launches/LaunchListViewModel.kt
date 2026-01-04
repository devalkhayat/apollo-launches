package com.example.apollo_launches.ui.screens.launches

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apollo_launches.data.model.LaunchSummaryDto
import com.example.apollo_launches.data.network.GraphQLService
import com.example.apollo_launches.domain.model.LaunchSummary
import com.example.apollo_launches.domain.usecase.GetLaunchesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LaunchListViewModel @Inject constructor(
    private val getLaunchesUseCase: GetLaunchesUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(LaunchListViewState())
    val state: StateFlow<LaunchListViewState> = _state.asStateFlow()

    // Keep track of pagination
    private var nextCursor: String? = null
    private var isLoadingMore = false

    fun handleIntent(intent: LaunchListIntent) {
        when (intent) {
            is LaunchListIntent.LoadLaunches -> loadLaunches()
            is LaunchListIntent.LoadNextPage -> loadNextPage(intent.pageSize)
            is LaunchListIntent.Refresh -> refresh(intent.pageSize)
        }
    }

    private fun loadLaunches(pageSize: Int = 20) {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true, error = null)
            try {
                val page = getLaunchesUseCase(pageSize, after = null)
                nextCursor = page.cursor
                _state.value = _state.value.copy(
                    isLoading = false,
                    launches = page.launches,
                    hasMore = page.hasMore
                )
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    isLoading = false,
                    error = e.message
                )
            }
        }
    }

    private fun loadNextPage(pageSize: Int = 20) {
        if (isLoadingMore || nextCursor == null) return
        isLoadingMore = true

        viewModelScope.launch {
            try {
                val page = getLaunchesUseCase(pageSize, after = nextCursor)
                nextCursor = page.cursor
                // Append new launches to existing list
                val updatedLaunches = _state.value.launches + page.launches
                _state.value = _state.value.copy(
                    launches = updatedLaunches,
                    hasMore = page.hasMore
                )
            } catch (e: Exception) {
                _state.value = _state.value.copy(error = e.message)
            } finally {
                isLoadingMore = false
            }
        }
    }

    private fun refresh(pageSize: Int = 20) {
        nextCursor = null
        loadLaunches(pageSize)
    }
}



