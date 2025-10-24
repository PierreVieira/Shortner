package com.pierre.shortner.feature.links.delete_all.data

import com.pierre.shortner.core.room_provider.dao.LinkDao
import com.pierre.shortner.feature.links.delete_all.domain.repository.DeleteAllLinksRepository

class DeleteAllLinksRepositoryImpl(
    private val dao: LinkDao
): DeleteAllLinksRepository {
    override suspend fun clear() {
        dao.clear()
    }
}
