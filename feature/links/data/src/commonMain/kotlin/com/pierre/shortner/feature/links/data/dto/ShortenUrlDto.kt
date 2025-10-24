package com.pierre.shortner.feature.links.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ShortenUrlDto(
    @SerialName("alias") val alias: String,
    @SerialName("_links") val linksDto: LinksDto
)
