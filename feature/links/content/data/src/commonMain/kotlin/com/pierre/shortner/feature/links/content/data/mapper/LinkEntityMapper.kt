package com.pierre.shortner.feature.links.content.data.mapper

import com.pierre.shortner.core.room_provider.entity.LinkEntity
import com.pierre.shortner.feature.links.content.domain.model.LinkDomainModel

fun interface LinkEntityMapper {
    fun map(entity: LinkEntity): LinkDomainModel
}
