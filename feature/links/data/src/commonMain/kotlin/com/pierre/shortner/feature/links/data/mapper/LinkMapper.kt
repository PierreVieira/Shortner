package com.pierre.shortner.feature.links.data.mapper

import com.pierre.shortner.core.room_provider.entity.LinkEntity
import com.pierre.shortner.feature.links.data.model.ShortenUrlResponse
import com.pierre.shortner.feature.links.domain.model.Link

fun ShortenUrlResponse.toDomain(): Link {
    return Link(
        id = 0, // Will be set by Room when inserted
        originalUrl = _links.self,
        shortenedUrl = _links.short,
        alias = alias
    )
}

fun LinkEntity.toDomain(): Link {
    return Link(
        id = id,
        originalUrl = originalLink,
        shortenedUrl = shortedLink,
        alias = shortedLink // Using shortedLink as alias for now
    )
}

fun Link.toEntity(): LinkEntity {
    return LinkEntity(
        id = id,
        originalLink = originalUrl,
        shortedLink = shortenedUrl
    )
}
