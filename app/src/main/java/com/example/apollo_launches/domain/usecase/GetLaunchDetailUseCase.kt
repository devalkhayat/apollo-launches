package com.example.apollo_launches.domain.usecase

import com.example.apollo_launches.domain.model.LaunchDetail
import com.example.apollo_launches.domain.repository.LaunchRepository
import jakarta.inject.Inject

class GetLaunchDetailUseCase @Inject constructor(private val repository: LaunchRepository) {
    suspend operator fun invoke(launchId: String): LaunchDetail =
        repository.getLaunchById(launchId)
}
