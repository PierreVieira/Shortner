package com.pierre.shortner.feature.links.domain.model

import kotlinx.datetime.LocalDateTime

data class Link(
    val id: Long,
    val originalUrl: String,
    val shortenedUrl: String,
    val alias: String,
    val createdAt: LocalDateTime
)
