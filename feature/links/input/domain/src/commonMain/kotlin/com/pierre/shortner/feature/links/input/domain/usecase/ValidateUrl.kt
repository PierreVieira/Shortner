package com.pierre.shortner.feature.links.input.domain.usecase

internal interface ValidateUrl {
    suspend operator fun invoke(url: String): Result<Unit>
}
