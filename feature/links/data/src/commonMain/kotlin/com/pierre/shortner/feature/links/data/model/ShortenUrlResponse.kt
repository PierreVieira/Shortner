package com.pierre.shortner.feature.links.data.model

import kotlinx.serialization.Serializable

@Serializable
data class ShortenUrlResponse(
    val alias: String,
    val _links: Links
)

@Serializable
data class Links(
    val self: String,
    val short: String
)
