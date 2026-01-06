package com.example.apollo_launches.ui.screens.launches

import androidx.paging.PagingData
import com.example.apollo_launches.domain.model.Launch
import kotlinx.coroutines.flow.Flow


data class LaunchState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)