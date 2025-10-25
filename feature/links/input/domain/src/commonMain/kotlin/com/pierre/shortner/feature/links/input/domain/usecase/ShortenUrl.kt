package com.pierre.shortner.feature.links.input.domain.usecase

import com.pierre.shortner.feature.links.input.domain.model.ShortenUrlStep
import kotlinx.coroutines.flow.Flow

fun interface ShortenUrl {
    operator fun invoke(url: String): Flow<ShortenUrlStep>
}
