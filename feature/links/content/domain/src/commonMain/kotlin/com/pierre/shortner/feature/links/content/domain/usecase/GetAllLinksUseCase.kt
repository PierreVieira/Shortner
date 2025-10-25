package com.pierre.shortner.feature.links.content.domain.usecase

import com.pierre.shortner.feature.links.content.domain.model.LinkDomainModel
import com.pierre.shortner.feature.links.content.domain.repository.LinksRepository
import kotlinx.coroutines.flow.Flow

class GetAllLinksUseCase(
    private val repository: LinksRepository
) {
    operator fun invoke(): Flow<List<LinkDomainModel>> = repository.getAllLinks()
}
