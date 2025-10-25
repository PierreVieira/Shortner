package com.pierre.shortner.feature.links.delete_link.data.repository

import com.pierre.shortner.core.room_provider.dao.LinkDao
import com.pierre.shortner.feature.links.delete_link.domain.repository.DeleteLinkRepository

class DeleteLinkRepositoryImpl(private val linkDao: LinkDao): DeleteLinkRepository {

    override suspend fun deleteLink(id: Long) {
        linkDao.deleteById(id)
    }
}
