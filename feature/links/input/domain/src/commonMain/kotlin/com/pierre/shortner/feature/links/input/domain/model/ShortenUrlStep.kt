package com.pierre.shortner.feature.links.input.domain.model

sealed interface ShortenUrlStep {
    data object InProgress: ShortenUrlStep
    data object Success: ShortenUrlStep
    data class Error(val throwable: Throwable): ShortenUrlStep
}
