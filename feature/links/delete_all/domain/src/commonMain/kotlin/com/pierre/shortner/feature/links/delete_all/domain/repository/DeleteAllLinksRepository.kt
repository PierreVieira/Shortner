package com.pierre.shortner.feature.links.delete_all.domain.repository

interface DeleteAllLinksRepository {
    suspend fun clear()
}
