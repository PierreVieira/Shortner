package com.pierre.shortner.feature.links.domain.model

data class Link(
    val id: Long,
    val originalUrl: String,
    val shortenedUrl: String,
    val alias: String
)
