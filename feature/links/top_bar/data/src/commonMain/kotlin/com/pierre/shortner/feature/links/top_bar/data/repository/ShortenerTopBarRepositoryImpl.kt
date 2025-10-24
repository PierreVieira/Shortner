package com.pierre.shortner.feature.links.top_bar.data.repository

import com.pierre.shortner.core.room_provider.dao.LinkDao
import com.pierre.shortner.feature.links.top_bar.domain.repository.ShortenerTopBarRepository

class ShortenerTopBarRepositoryImpl(
    private val dao: LinkDao,
): ShortenerTopBarRepository {
    override suspend fun getLinks(): List<Long> = dao.getAll().map { it.id }
}
