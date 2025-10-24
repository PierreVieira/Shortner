package com.pierre.shortner.feature.links.domain.usecase

import com.pierre.shortner.feature.links.domain.model.Link
import com.pierre.shortner.feature.links.domain.repository.LinksRepository
import kotlinx.coroutines.flow.Flow

class GetAllLinksUseCase(
    private val repository: LinksRepository
) {
    operator fun invoke(): Flow<List<Link>> = repository.getAllLinks()
}
