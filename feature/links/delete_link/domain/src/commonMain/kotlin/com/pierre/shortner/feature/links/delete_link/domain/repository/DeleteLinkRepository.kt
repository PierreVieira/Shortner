package com.pierre.shortner.feature.links.delete_link.domain.repository

interface DeleteLinkRepository {
    suspend fun deleteLink(id: Long)
}
