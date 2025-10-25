package com.pierre.shortner.feature.links.input.domain.usecase.impl

import com.pierre.shortner.feature.links.input.domain.repository.LinkInputRepository
import com.pierre.shortner.feature.links.input.domain.usecase.IsLinkAlreadyAdded

class IsLinkAlreadyAddedUseCase(
    private val repository: LinkInputRepository,
) : IsLinkAlreadyAdded {
    override suspend operator fun invoke(originalUrl: String): Boolean =
        repository.getAllOriginalLinks().contains(originalUrl)
}
