package com.pierre.shortner.feature.links.input.domain.usecase.impl

import com.pierre.shortner.feature.links.input.domain.model.UrlValidationException
import com.pierre.shortner.feature.links.input.domain.usecase.IsLinkAlreadyAdded
import com.pierre.shortner.feature.links.input.domain.usecase.ValidateUrl

class ValidateUrlUseCase(
    private val isLinkAlreadyAdded: IsLinkAlreadyAdded
) : ValidateUrl {
    override suspend operator fun invoke(url: String): Result<Unit> = runCatching {
        when {
            url.isBlank() -> throw UrlValidationException.Empty()
            isLinkAlreadyAdded(url) -> throw UrlValidationException.LinkAlreadyAdded()
            !isValidUrl(url) -> throw UrlValidationException.Invalid()
            else -> Unit
        }
    }

    private fun isValidUrl(url: String): Boolean = try {
        val urlPattern = Regex("^https?://.*")
        urlPattern.matches(url)
    } catch (_: Exception) {
        false
    }
}
