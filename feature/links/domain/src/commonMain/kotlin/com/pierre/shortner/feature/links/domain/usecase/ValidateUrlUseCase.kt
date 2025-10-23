package com.pierre.shortner.feature.links.domain.usecase

class ValidateUrlUseCase {
    operator fun invoke(url: String): ValidationResult {
        return when {
            url.isBlank() -> ValidationResult.Empty
            !isValidUrl(url) -> ValidationResult.Invalid
            else -> ValidationResult.Valid
        }
    }
    
    private fun isValidUrl(url: String): Boolean = try {
        val urlPattern = Regex("^https?://.*")
        urlPattern.matches(url)
    } catch (_: Exception) {
        false
    }
}

sealed interface ValidationResult {
    data object Valid : ValidationResult
    data object Empty : ValidationResult
    data object Invalid : ValidationResult
}
