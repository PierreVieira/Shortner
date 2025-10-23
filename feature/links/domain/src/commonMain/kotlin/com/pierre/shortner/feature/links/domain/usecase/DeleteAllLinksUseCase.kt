package com.pierre.shortner.feature.links.domain.usecase

import com.pierre.shortner.feature.links.domain.repository.LinksRepository

class DeleteAllLinksUseCase(
    private val repository: LinksRepository
) {
    suspend operator fun invoke() {
        repository.deleteAllLinks()
    }
}
