package com.example.apollo_launches.domain.paging

import com.example.apollo_launches.domain.model.Launch

data class CursorPage(
    val items: List<Launch?> ,
    val nextCursor: String? ,
    val hasMore: Boolean
)