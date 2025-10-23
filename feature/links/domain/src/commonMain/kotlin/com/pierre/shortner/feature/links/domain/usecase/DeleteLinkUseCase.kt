package com.pierre.shortner.feature.links.domain.usecase

import com.pierre.shortner.feature.links.domain.repository.LinksRepository

class DeleteLinkUseCase(
    private val repository: LinksRepository
) {
    suspend operator fun invoke(id: Long) {
        repository.deleteLink(id)
    }
}
