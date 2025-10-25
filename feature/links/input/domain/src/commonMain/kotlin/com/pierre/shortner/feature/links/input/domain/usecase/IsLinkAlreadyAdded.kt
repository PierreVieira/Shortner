package com.pierre.shortner.feature.links.input.domain.usecase

fun interface IsLinkAlreadyAdded {
    suspend operator fun invoke(originalUrl: String): Boolean
}
