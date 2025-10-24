package com.pierre.shortner.feature.links.presentation.model

data class LinkPresentationModel(
    val id: Long,
    val originalUrl: String,
    val shortenedUrl: String,
    val alias: String,
    val createdAt: String,
    val isCardExpanded: Boolean = false,
    val isMenuExpanded: Boolean = false,
)
