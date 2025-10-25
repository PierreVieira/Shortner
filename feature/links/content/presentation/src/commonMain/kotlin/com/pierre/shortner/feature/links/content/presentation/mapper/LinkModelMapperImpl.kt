package com.pierre.shortner.feature.links.content.presentation.mapper

import com.pierre.shortner.core.utils.toFormattedString
import com.pierre.shortner.feature.links.content.domain.model.LinkDomainModel
import com.pierre.shortner.feature.links.content.presentation.model.LinkPresentationModel
import com.pierre.shortner.ui.utils.string_provider.StringProvider
import shortener.feature.links.content.presentation.generated.resources.Res
import shortener.feature.links.content.presentation.generated.resources.at

class LinkModelMapperImpl(
    private val stringProvider: StringProvider
) : LinkModelMapper {
    override suspend fun toPresentation(domainModel: LinkDomainModel): LinkPresentationModel =
        domainModel.run {
            LinkPresentationModel(
                id = id,
                originalUrl = originalUrl,
                shortenedUrl = shortenedUrl,
                alias = alias,
                createdAt = createdAt.toFormattedString(stringProvider.getString(Res.string.at)),
                isCardExpanded = false,
                isMenuExpanded = false
            )
        }
}
