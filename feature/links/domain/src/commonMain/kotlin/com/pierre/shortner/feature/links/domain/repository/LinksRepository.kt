package com.pierre.shortner.feature.links.domain.repository

import com.pierre.shortner.feature.links.domain.model.Link
import kotlinx.coroutines.flow.Flow

interface LinksRepository {
    fun getAllLinks(): Flow<List<Link>>
    suspend fun deleteLink(id: Long)
    suspend fun deleteAllLinks()
}
