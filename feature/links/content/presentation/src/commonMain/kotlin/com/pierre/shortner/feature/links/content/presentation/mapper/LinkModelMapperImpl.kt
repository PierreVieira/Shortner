package com.pierre.shortner.feature.links.content.presentation.mapper

import com.pierre.shortner.core.utils.toFormattedString
import com.pierre.shortner.feature.links.content.domain.model.LinkDomainModel
import com.pierre.shortner.feature.links.content.presentation.model.LinkPresentationModel
import org.jetbrains.compose.resources.getString
import shortener.feature.links.content.presentation.generated.resources.Res
import shortener.feature.links.content.presentation.generated.resources.at

class LinkModelMapperImpl : LinkModelMapper {
    override suspend fun toPresentation(domainModel: LinkDomainModel): LinkPresentationModel =
        domainModel.run {
            LinkPresentationModel(
                id = id,
                originalUrl = originalUrl,
                shortenedUrl = shortenedUrl,
                alias = alias,
                createdAt = createdAt.toFormattedString(getString(Res.string.at)),
                isCardExpanded = false,
                isMenuExpanded = false
            )
        }
}
