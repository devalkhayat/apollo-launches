package com.example.apollo_launches.domain.usecase

import com.example.apollo_launches.domain.repository.LaunchRepository
import jakarta.inject.Inject

class GetLaunchesUseCase @Inject constructor(private val repository: LaunchRepository) {
    suspend operator fun invoke(pageSize: Int, after: String?) =
        repository.getLaunches(pageSize, after)
}
