package com.example.apollo_launches.domain.usecase

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.apollo_launches.data.paging.LaunchPagingSource
import com.example.apollo_launches.domain.model.Launch
import com.example.apollo_launches.domain.paging.CursorPage
import com.example.apollo_launches.domain.repository.LaunchRepository
import kotlinx.coroutines.flow.Flow

import javax.inject.Inject

class GetLaunchesPageUseCase @Inject constructor(
    private val repository: LaunchRepository
) {

    operator fun invoke(pageSize: Int = 10): Flow<PagingData<Launch>> {
        return Pager(
            config = PagingConfig(
                pageSize = pageSize ,
                enablePlaceholders = false
            ) ,
            pagingSourceFactory = { LaunchPagingSource(repository , pageSize) }
        ).flow
    }
}


