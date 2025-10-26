package com.pierre.shortner.feature.links.input.data.mapper

import com.pierre.shortner.core.room_provider.entity.LinkEntity
import com.pierre.shortner.feature.links.input.data.dto.ShortenUrlDto

fun interface ShortenUrlDtoMapper {
    fun toEntity(dto: ShortenUrlDto): LinkEntity
}
