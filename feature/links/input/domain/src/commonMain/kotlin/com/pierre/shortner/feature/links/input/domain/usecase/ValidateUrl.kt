package com.pierre.shortner.feature.links.input.domain.usecase

interface ValidateUrl {
    suspend operator fun invoke(url: String): Result<Unit>
}
