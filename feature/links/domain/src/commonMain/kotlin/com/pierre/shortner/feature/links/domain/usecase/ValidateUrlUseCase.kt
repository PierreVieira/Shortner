package com.pierre.shortner.feature.links.domain.usecase

import com.pierre.shortner.feature.links.domain.model.UrlValidationException

class ValidateUrlUseCase {
    operator fun invoke(url: String): Result<Unit> = runCatching {
        when {
            url.isBlank() -> throw UrlValidationException.Empty()
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
