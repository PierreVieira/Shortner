package com.pierre.shortner.feature.links.content.domain.model

import kotlinx.datetime.LocalDateTime

data class LinkDomainModel(
    val id: Long,
    val originalUrl: String,
    val shortenedUrl: String,
    val alias: String,
    val createdAt: LocalDateTime
)
