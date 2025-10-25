package com.pierre.shortner.feature.links.content.data.repository

import com.pierre.shortner.core.room_provider.dao.LinkDao
import com.pierre.shortner.core.room_provider.entity.LinkEntity
import com.pierre.shortner.feature.links.content.domain.model.LinkDomainModel
import com.pierre.shortner.feature.links.content.domain.repository.LinksRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

class LinksRepositoryImpl(
    private val linkDao: LinkDao,
) : LinksRepository {

    override fun getAllLinks(): Flow<List<LinkDomainModel>> =
        linkDao.getAllAsFlow().map { entities ->
            entities.map { it.toDomain() }
        }

    override suspend fun deleteLink(id: Long) {
        linkDao.deleteById(id)
    }

    private fun LinkEntity.toDomain(): LinkDomainModel = LinkDomainModel(
        id = id,
        originalUrl = originalLink,
        shortenedUrl = shortedLink,
        alias = alias,
        createdAt = Instant.fromEpochMilliseconds(createdAt)
            .toLocalDateTime(TimeZone.currentSystemDefault())
    )

}
