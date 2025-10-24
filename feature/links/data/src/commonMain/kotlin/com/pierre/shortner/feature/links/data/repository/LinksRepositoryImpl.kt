package com.pierre.shortner.feature.links.data.repository

import com.pierre.shortner.core.room_provider.dao.LinkDao
import com.pierre.shortner.core.room_provider.entity.LinkEntity
import com.pierre.shortner.feature.links.domain.model.Link
import com.pierre.shortner.feature.links.domain.repository.LinksRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

class LinksRepositoryImpl(
    private val linkDao: LinkDao,
) : LinksRepository {

    override fun getAllLinks(): Flow<List<Link>> = linkDao.getAllAsFlow().map { entities ->
        entities.map { it.toDomain() }
    }

    override suspend fun deleteLink(id: Long) {
        linkDao.deleteById(id)
    }

    override suspend fun deleteAllLinks() {
        linkDao.clear()
    }

    private fun LinkEntity.toDomain(): Link = Link(
        id = id,
        originalUrl = originalLink,
        shortenedUrl = shortedLink,
        alias = alias,
        createdAt = Instant.fromEpochMilliseconds(createdAt).toLocalDateTime(TimeZone.currentSystemDefault())
    )

}
