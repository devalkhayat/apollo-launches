package com.example.apollo_launches.ui.screens.launches

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.apollo_launches.domain.model.Launch
import com.example.apollo_launches.domain.usecase.GetLaunchesPageUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject



@HiltViewModel
class LaunchViewModel @Inject constructor(
    private val getLaunchesPageUseCase: GetLaunchesPageUseCase
) : ViewModel() {

    // Paging flow (data stream)
    val launchesPagingFlow: Flow<PagingData<Launch>> =
        getLaunchesPageUseCase()
            .cachedIn(viewModelScope)

    // MVI state
    private val _state = MutableStateFlow(LaunchState())
    val state: StateFlow<LaunchState> = _state.asStateFlow()

    fun onIntent(intent: LaunchIntent) {
        when (intent) {
            LaunchIntent.ScreenOpened -> {
                _state.update {
                    it.copy(isLoading = true, errorMessage = null)
                }
            }

            LaunchIntent.Retry -> {
                _state.update {
                    it.copy(isLoading = true, errorMessage = null)
                }
            }

            LaunchIntent.Refresh -> {
                _state.update {
                    it.copy(isLoading = true, errorMessage = null)
                }
            }
        }
    }

    fun onPagingLoaded() {
        _state.update { it.copy(isLoading = false) }
    }

    fun onPagingError(message: String) {
        _state.update {
            it.copy(isLoading = false, errorMessage = message)
        }
    }
}
