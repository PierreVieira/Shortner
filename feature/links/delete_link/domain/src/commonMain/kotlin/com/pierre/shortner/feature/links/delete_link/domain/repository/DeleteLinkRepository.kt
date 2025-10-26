package com.pierre.shortner.feature.links.delete_link.domain.repository

fun interface DeleteLinkRepository {
    suspend fun deleteLink(id: Long)
}
