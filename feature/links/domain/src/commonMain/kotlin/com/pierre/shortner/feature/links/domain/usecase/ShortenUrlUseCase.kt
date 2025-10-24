package com.pierre.shortner.feature.links.domain.usecase

import com.pierre.shortner.feature.links.domain.repository.LinksRepository

class ShortenUrlUseCase(
    private val repository: LinksRepository
) {
    suspend operator fun invoke(url: String): Result<Unit> = repository.postUrl(url)
}
