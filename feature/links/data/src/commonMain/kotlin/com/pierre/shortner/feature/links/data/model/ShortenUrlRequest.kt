package com.pierre.shortner.feature.links.data.model

import kotlinx.serialization.Serializable

@Serializable
data class ShortenUrlRequest(
    val url: String
)
