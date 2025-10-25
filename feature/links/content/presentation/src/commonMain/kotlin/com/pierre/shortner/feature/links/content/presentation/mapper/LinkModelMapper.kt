package com.pierre.shortner.feature.links.content.presentation.mapper

import com.pierre.shortner.feature.links.content.domain.model.LinkDomainModel
import com.pierre.shortner.feature.links.content.presentation.model.LinkPresentationModel

fun interface LinkModelMapper {
    suspend fun toPresentation(domainModel: LinkDomainModel): LinkPresentationModel
}
