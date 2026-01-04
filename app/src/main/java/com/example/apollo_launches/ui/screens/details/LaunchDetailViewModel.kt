package com.example.apollo_launches.ui.screens.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apollo_launches.domain.usecase.GetLaunchDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LaunchDetailViewModel @Inject constructor(
    private val getLaunchDetailUseCase: GetLaunchDetailUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(LaunchDetailViewState())
    val state: StateFlow<LaunchDetailViewState> = _state.asStateFlow()

    fun handleIntent(intent: LaunchDetailIntent) {
        when (intent) {
            is LaunchDetailIntent.LoadLaunch -> loadLaunch(intent.launchId)
            LaunchDetailIntent.Retry -> {
                _state.value.launch?.id?.let { loadLaunch(it) }
            }
        }
    }

    private fun loadLaunch(launchId: String) {
        viewModelScope.launch {
            _state.value = _state.value.copy(
                isLoading = true,
                error = null
            )
            try {
                val launch = getLaunchDetailUseCase(launchId)
                _state.value = _state.value.copy(
                    isLoading = false,
                    launch = launch
                )
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    isLoading = false,
                    error = e.message ?: "Unknown error"
                )
            }
        }
    }
}
