package com.example.apollo_launches.ui.screens.details

import com.example.apollo_launches.domain.model.LaunchDetail

data class LaunchDetailViewState(
    val isLoading: Boolean = false ,
    val launch: LaunchDetail? = null ,
    val error: String? = null
)