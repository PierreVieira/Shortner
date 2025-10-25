package com.pierre.shortner.feature.links.content.data.mapper

import com.pierre.shortner.core.room_provider.entity.LinkEntity
import com.pierre.shortner.core.utils.date.LocalDateTimeProvider
import com.pierre.shortner.feature.links.content.domain.model.LinkDomainModel

class LinkEntityMapperImpl(
    private val localDateTimeProvider: LocalDateTimeProvider,
): LinkEntityMapper {
    override fun map(entity: LinkEntity): LinkDomainModel = entity.run {
        LinkDomainModel(
            id = id,
            originalUrl = originalLink,
            shortenedUrl = shortedLink,
            alias = alias,
            createdAt = localDateTimeProvider.getLocalDateTime(createdAt)
        )
    }
}
