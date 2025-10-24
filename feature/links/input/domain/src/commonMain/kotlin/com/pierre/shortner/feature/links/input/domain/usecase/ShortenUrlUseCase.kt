package com.pierre.shortner.feature.links.input.domain.usecase

import com.pierre.shortner.feature.links.input.domain.repository.LinkInputRepository

class ShortenUrlUseCase(
    private val repository: LinkInputRepository,
) {
    suspend operator fun invoke(url: String): Result<Unit> = repository.postUrl(url)
}
