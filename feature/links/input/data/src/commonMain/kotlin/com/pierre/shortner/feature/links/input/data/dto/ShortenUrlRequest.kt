package com.pierre.shortner.feature.links.input.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ShortenUrlRequest(
    @SerialName("url") val url: String
)
