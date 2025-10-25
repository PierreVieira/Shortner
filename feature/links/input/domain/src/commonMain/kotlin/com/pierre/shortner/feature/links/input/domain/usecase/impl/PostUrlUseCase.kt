package com.pierre.shortner.feature.links.input.domain.usecase.impl

import com.pierre.shortner.feature.links.input.domain.repository.LinkInputRepository
import com.pierre.shortner.feature.links.input.domain.usecase.PostUrl

class PostUrlUseCase(
    private val repository: LinkInputRepository,
) : PostUrl {
    override suspend operator fun invoke(url: String): Result<Unit> = repository.postUrl(url)
}
