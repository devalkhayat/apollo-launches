package com.example.apollo_launches.ui.screens.launches

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.apollo_launches.domain.model.Launch
import com.example.apollo_launches.domain.paging.CursorPage
import com.example.apollo_launches.domain.usecase.GetLaunchesPageUseCase


import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject



@HiltViewModel
class LaunchViewModel @Inject constructor(
    private val getLaunchesPageUseCase: GetLaunchesPageUseCase
) : ViewModel() {

    // Expose directly the PagingData as state
    val launchesPagingFlow: Flow<PagingData<Launch>> =
        getLaunchesPageUseCase()
            .cachedIn(viewModelScope)  // cache PagingData in ViewModel scope

    private val _state = MutableStateFlow(LaunchState(isLoading = true))
    val state: StateFlow<LaunchState> = _state.asStateFlow()

   /*fun handleIntent(intent: LaunchIntent) {
        when (intent) {
            is LaunchIntent.LoadLaunches -> {
                // no need to assign flow again; just update loading state
                _state.update { it.copy(isLoading = true, errorMessage = null) }
            }
            is LaunchIntent.RefreshLaunches -> {
                _state.update { it.copy(isLoading = true, errorMessage = null) }
            }



        }
    }*/
}
