package com.example.apollo_launches.data.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.apollo_launches.domain.model.Launch
import com.example.apollo_launches.domain.paging.CursorPage
import com.example.apollo_launches.domain.repository.LaunchRepository

class LaunchPagingSource(
    private val repository: LaunchRepository,
    private val pageSize: Int
) : PagingSource<String, Launch>() {

    override suspend fun load(
        params: LoadParams<String>
    ): LoadResult<String, Launch> {
        return try {
            // Get the current cursor (null for first page)
            val cursor = params.key
            Log.d("PagingSource" , "Loading page. Cursor=$cursor, pageSize=$pageSize")
            // Use the domain repository to get the page
            val page: CursorPage = repository.getLaunches(
                pageSize = pageSize,
                cursor = cursor
            )
            Log.d("PagingSource", "Received page.items=${page.items.size}, nextCursor=${page.nextCursor}")
            // Convert CursorPage to Paging 3 Page
            val nonNullItems = page.items.filterNotNull()
            LoadResult.Page(
                data = nonNullItems ,
                prevKey = null , // cursor paging does not support backward
                nextKey = if (page.hasMore) page.nextCursor else null
            )

        } catch (e: Exception) {
            Log.e("PagingSource", "Error loading page: ${e.message}")
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<String, Launch>): String? {
        // For cursor paging, usually refresh from the first page
        return null
    }
}