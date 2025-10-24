package com.pierre.shortner.feature.links.input.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LinksDto(
    @SerialName("self") val original: String,
    @SerialName("short") val short: String
)
