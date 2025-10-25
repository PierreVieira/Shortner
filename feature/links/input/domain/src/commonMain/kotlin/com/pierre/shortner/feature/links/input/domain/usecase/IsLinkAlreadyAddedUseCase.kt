package com.pierre.shortner.feature.links.input.domain.usecase

import com.pierre.shortner.feature.links.input.domain.repository.LinkInputRepository

class IsLinkAlreadyAddedUseCase(
    private val repository: LinkInputRepository,
) {
    suspend operator fun invoke(originalUrl: String): Boolean {
        return repository.getAllOriginalLinks().contains(originalUrl)
    }
}
