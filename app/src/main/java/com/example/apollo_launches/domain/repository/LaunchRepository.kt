package com.example.apollo_launches.domain.repository

import androidx.paging.PagingData
import com.example.apollo_launches.domain.model.LaunchDetail
import com.example.apollo_launches.domain.model.Launch
import com.example.apollo_launches.domain.paging.CursorPage
import kotlinx.coroutines.flow.Flow


interface LaunchRepository {


    // Get full details of a single launch
    suspend fun getLaunchById(launchId: String): LaunchDetail

    //Get list of launches (summary) with pagination
    suspend fun getLaunches(
        pageSize: Int,
        cursor: String?
    ): CursorPage
}
